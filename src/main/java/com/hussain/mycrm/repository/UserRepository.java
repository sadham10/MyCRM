package com.hussain.mycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hussain.mycrm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByUserName(String userName);

}
