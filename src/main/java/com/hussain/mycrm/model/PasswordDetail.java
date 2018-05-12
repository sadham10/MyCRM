package com.hussain.mycrm.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="password_vault")
public class PasswordDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	
	@Column(name="user_id", nullable=false)
	private Long userId;
	
	@NotEmpty
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="modification_date")
	private Date modificationDate;
	
	@Column(name="expiry_date")
	private Date ExpiryDate;
	
	@Column(name="is_active")
	private boolean isActive;
	
	
	
	public PasswordDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordDetail(long id, long userId, String password, Date creationDate, Date modificationDate,
			Date expiryDate, boolean isActive) {
		super();
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		ExpiryDate = expiryDate;
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getUser() {
		return userId;
	}

	public void setUser(long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Date getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		ExpiryDate = expiryDate;
	}
	
	

}
