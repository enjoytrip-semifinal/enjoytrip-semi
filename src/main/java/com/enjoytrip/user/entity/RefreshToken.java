package com.enjoytrip.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
public class RefreshToken {
	
	@Id
	private String id;
	
	@Column(nullable = false, unique = true)
	private String refreshtoken;
	
//	@Column(nullable = false)
//	private Instant expiryDate;
//	
}


