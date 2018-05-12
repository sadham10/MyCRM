package com.hussain.mycrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ROLE")
public class Role {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="ROLE_ID")
	private long roleId;
	
	@Column(name="ROLE_CODE")
	private String roleCode;
	
	@Column(name="ROLE_DESC")
	private String roleDescription;
	
	@Column(name="IS_ACTIVE")
	private boolean isActive;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Role(long roleId, String roleCode, String roleDescription, boolean isActive) {
		super();
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleDescription = roleDescription;
		this.isActive = isActive;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
