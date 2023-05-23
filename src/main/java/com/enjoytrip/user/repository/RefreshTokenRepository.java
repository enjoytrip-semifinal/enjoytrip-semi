package com.enjoytrip.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enjoytrip.user.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findByrefreshtoken(String refreshToken);
	void deleteByrefreshtoken(String refreshToken);
	void deleteByid(String id);
	
}
 