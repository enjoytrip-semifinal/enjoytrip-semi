package com.enjoytrip.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enjoytrip.user.entity.UserDto;


public interface UserRespository extends JpaRepository<UserDto, Long>{
	// https://zara49.tistory.com/130
	Optional<UserDto> findByid(String name);
	
	Optional<UserDto> findByemail(String email);
	
	@Query(value = "SELECT roles FROM user_roles WHERE user_id = ?1", nativeQuery = true)
	List<String> findRolesById(String id);
}
