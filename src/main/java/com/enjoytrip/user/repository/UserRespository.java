package com.enjoytrip.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enjoytrip.user.entity.UserDto;

public interface UserRespository extends JpaRepository<UserDto, Long>{
	Optional<UserDto> findByid(String name);
}
