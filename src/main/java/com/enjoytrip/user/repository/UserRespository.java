package com.enjoytrip.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enjoytrip.user.entity.UserDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;


public interface UserRespository extends JpaRepository<UserDto, Long>{
	// https://zara49.tistory.com/130
	Optional<UserDto> findByid(String id);
	
	List<UserDto> findByemail(String email);
	
	//@Query(value = "SELECT nickname, password, email, address WHERE id = ?1", nativeQuery = true)
	
	@Query(value = "SELECT user_id FROM user WHERE id = ?1", nativeQuery = true)
	int finduser_idbyId(String id);
	
	@Query(value = "SELECT roles FROM user_roles WHERE user_id = ?1", nativeQuery = true)
	List<String> findRolesById(String id);
}
