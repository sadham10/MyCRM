package com.hussain.mycrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hussain.mycrm.model.PasswordDetail;

public interface PasswordRepository extends JpaRepository<PasswordDetail, Long> {

	
	PasswordDetail findByUserIdAndIsActiveTrue(long userId);

}
