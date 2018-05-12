package com.hussain.mycrm.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hussain.mycrm.model.PasswordDetail;
import com.hussain.mycrm.model.User;

@Service
public interface UserService {
	

	public User getUserByUsername(String username);

	public User saveUser(User user);
	
	public List<PasswordDetail> getPasswordHistroy();
	
	public PasswordDetail getCurrentPassword();

	public User createUser(User user,String password);
	
	

}
