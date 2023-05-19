package com.enjoytrip.user.controller;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String id;
    private String password;
}
