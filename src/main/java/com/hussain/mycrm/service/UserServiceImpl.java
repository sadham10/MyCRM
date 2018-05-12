package com.hussain.mycrm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hussain.mycrm.model.PasswordDetail;
import com.hussain.mycrm.model.User;
import com.hussain.mycrm.repository.PasswordRepository;
import com.hussain.mycrm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordRepository pwdRepository;
	
	
	@Override
	public User getUserByUsername(String username) {
		User user=userRepository.findByUserName(username);
		/*PasswordDetail pwddetl=pwdRepository.findByUserIdAndIsActiveTrue(user.getUserId());
		user.setPassword(pwddetl.getPassword());*/
		return user;
	}



	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}



	@Override
	public List<PasswordDetail> getPasswordHistroy() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public PasswordDetail getCurrentPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public User createUser(User user,String password) {
		user=userRepository.save(user);
		PasswordDetail pwdDtl=new PasswordDetail();
		pwdDtl.setActive(true);
		pwdDtl.setCreationDate(new Date());
		pwdDtl.setModificationDate(new Date());
		pwdDtl.setExpiryDate(new Date());
		pwdDtl.setUser(user.getUserId());
		pwdDtl.setPassword(generateEncryptedPwd(password));
		pwdRepository.save(pwdDtl);
		return user;
	}

	private String generateEncryptedPwd(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

}
