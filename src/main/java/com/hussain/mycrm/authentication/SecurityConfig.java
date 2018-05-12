package com.hussain.mycrm.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



	
	@Autowired
	private AuthenticationTokenEntryPoint authenticationEntryPoint;
	
	
	
//	@Bean
//	AuthenticationTokenFilter getAuthenticationTokenFilter(){
//		return new AuthenticationTokenFilter();		
//	}
	
	/*@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
		auth..userDetailsService(userService)
			.passwordEncoder(passwordEncoder());
		
	}	
	
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and()
				
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/auth").permitAll()
			.antMatchers("/api/getAllCustomers").authenticated()
			.antMatchers("/api/**").authenticated()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
				
			.and()
				.exceptionHandling()
				//.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
			/*.and()
				.formLogin()
				.loginProcessingUrl(HttpPathStore.LOGIN)
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.permitAll()*/
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				//.logoutSuccessHandler(logoutSuccessHandler)
				.permitAll();
		
		 http.addFilterBefore(new AuthenticationTokenFilter(), BasicAuthenticationFilter.class);

		
	}
	
}