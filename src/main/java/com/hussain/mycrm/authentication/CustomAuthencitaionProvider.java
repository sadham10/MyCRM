package com.hussain.mycrm.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import com.hussain.mycrm.model.Role;
import com.hussain.mycrm.model.User;
import com.hussain.mycrm.repository.UserRepository;
import com.hussain.mycrm.service.UserService;

@Component
public class CustomAuthencitaionProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthencitaionProvider.class);
	


	@Autowired
	private UserService userService;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			
			LOGGER.debug("authenticate of CustomAuthenticationProvider");
			String username = (String) authentication.getPrincipal();
			if(StringUtils.isBlank(username)) {
				LOGGER.warn("Username is empty");
				throw new InsufficientAuthenticationException("Username can not be empty..");
			}
			User user = userService.getUserByUsername(username);
			
			if(user == null) {
				LOGGER.warn("No such user with the given username "+username);
				throw new LockedException("No such user with the given username "+username);
			}
			String password = (String)authentication.getCredentials();
	
			String currentPwd=user.getPassword();
			//userService.getCurrentPassword().getPassword();

			
			LOGGER.info("password : : "+password);
			LOGGER.info("currentPwd : : "+currentPwd);
			if(!password.equals(currentPwd)) {
				LOGGER.warn("Invalid credentials..");
				throw new BadCredentialsException("Invalid credentials..");
			}
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (Role role:user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
			}
			return new UsernamePasswordAuthenticationToken(user,currentPwd,authorities);
			
		} catch (Exception e) {
			LOGGER.error("Error while authenticating user",e);
			throw e;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}