package com.hussain.mycrm.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.hussain.mycrm.model.Role;
import com.hussain.mycrm.model.User;



public class AuthenticationTokenFilter extends GenericFilterBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

	/*	@Autowired
	private UserService userService;
	 */

	private JwtTokenUtil jwtTokenUtil;

	//@Value("${auth.token.header}")
	private String tokenHeader="X-AUTH";


	/*private AuthenticationManager authenticationManager;


	public AuthenticationTokenFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}*/


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {

		try{
			LOGGER.debug("*****Request URI**********"+((HttpServletRequest)request).getRequestURI());

			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String authToken = httpRequest.getHeader(this.tokenHeader);	
			LOGGER.debug("authToken ::"+authToken);
			jwtTokenUtil=new JwtTokenUtil();
			LOGGER.debug("jwtTokenUtil.equals(null) ::"+jwtTokenUtil.equals(null));

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				if (authToken !=null && jwtTokenUtil.validateToken(authToken)) {
					User userDetails = jwtTokenUtil.getUserFromToken(authToken);
					LOGGER.debug("userDetails.getRoles().toArray() :::userDetails.getUserName() ::"+userDetails.getRoles().toArray() +":::"
							+ userDetails.equals(null));
					Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					for (Role role:userDetails.getRoles()) {
						authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
					}
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, authorities);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			chain.doFilter(request, response);

		}catch(Exception e){
			LOGGER.debug("Exception Occured!! ::",e);
			e.printStackTrace();
		}
	}

}