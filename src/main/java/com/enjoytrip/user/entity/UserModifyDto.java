package com.enjoytrip.user.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
public class UserModifyDto {
	private String nickname;
	private String password;
	private String email;
	private String address;
}
