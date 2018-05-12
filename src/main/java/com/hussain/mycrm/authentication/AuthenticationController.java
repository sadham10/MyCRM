package com.hussain.mycrm.authentication;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hussain.mycrm.model.User;
import com.hussain.mycrm.service.UserService;


@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins={"http://localhost:4200","http://localhost:4201","http://localhost:8200"},allowCredentials="true",allowedHeaders="true",exposedHeaders={"X-AUTH"})
public class AuthenticationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Value("${auth.token.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;

	@RequestMapping(value = "${auth.token.authentication.path}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestParam("username") String username,
			@RequestParam("password") String password) throws AuthenticationException {
		try {
			LOGGER.debug("Inside authentication username:"+username);
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final User userDetails =  (User) authentication.getPrincipal();
			userDetails.setPassword(password);
			LOGGER.debug("userDetails.getUserName() :::userDetails.getUserName() ::"+userDetails.getUserName() +":::"
					+ userDetails.equals(null));
			final String token = jwtTokenUtil.generateToken(userDetails, null);
			HttpHeaders headers = new HttpHeaders();
			headers.add(tokenHeader, token);
			return new ResponseEntity<User>(userDetails,headers,HttpStatus.OK);
		}  catch (ProviderNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This type of  authentication not supported");
		} catch (AuthenticationException e) {
			LOGGER.debug("******"+e.getClass().getCanonicalName());
			LOGGER.debug("******"+e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
			
		} catch ( Exception e) {
			if(e.getMessage() == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to authenticate due to depedent services being down");
		}
	}

	@RequestMapping(value = "${auth.token.refresh.path}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		LOGGER.debug("Inside refresh token:"+token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		LOGGER.debug("username from token:"+username);
		User user = userDetailsService.getUserByUsername(username);
		if(user == null) {
			return ResponseEntity.badRequest().body("Invalid token, and hence can't be refreshed.");
		}
		if (jwtTokenUtil.canTokenBeRefreshed(token, new Date())) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(refreshedToken);
		} else {
			return ResponseEntity.badRequest().body("The given token not yet expired");
		}
	}
	
	@RequestMapping(value = "/sayHello", method = RequestMethod.GET)
	@ResponseBody public String sayHello() {
		return "Hello Shaik";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	@ResponseBody public String sayWelcome() {
		return "Welcome Shaik";
	}

}
