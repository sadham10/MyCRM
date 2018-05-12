package com.hussain.mycrm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

@Entity
@Table(name="USER")
public class User  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="USER_ID", nullable=false)
	private long userId;
	
	@NotEmpty
	@Column(name="USER_NAME", nullable=false)
	private String userName;
	
	@Column(name="MOBILE_NUMBER", nullable=false)
	private long mobileNumber;
	
	@Column(name="IS_ACTIVE", nullable=false)
	private boolean isActive;
	
	@JoinTable(name="USER_ROLES",joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Role> roles;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;


	public String getPassword() {
		return password;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	

	public void setPassword(String password) {
		this.password = password;
	}

	public User(long userId, String userName, long mobileNumber, boolean isActive, Set<Role> roles) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.mobileNumber = mobileNumber;
		this.isActive = isActive;
		this.roles = roles;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


}
