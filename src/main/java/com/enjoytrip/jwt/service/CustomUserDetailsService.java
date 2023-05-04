package com.enjoytrip.jwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enjoytrip.user.entity.UserDto;
import com.enjoytrip.user.repository.UserRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRespository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		log.info("UserbyUsername : {}", id);
		return userRepository.findByid(id)
				.map(this::createUserDetails)
				.orElseThrow( () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
				
	}

	// 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
	/**
	 * 여기서 PasswordEncoder를 통해 UserDetails 객체를 생성할 때 encoding을 해줌 => 왜냐하면 Spring
	 * Security는 사용자 검증을 위해 encoding된 password와 그렇지 않은 password를 비교하기 때문 실제로는 DB 자체에
	 * encoding된 password 값을 갖고 있고 그냥 memer.getPassword()로 encoding된 password를 꺼내는
	 * 것이 좋지만, 예제에서는 편의를 위해 검증 객체를 생성할 때 encoding을 해줌.
	 */
	private UserDetails createUserDetails(UserDto userDto) {
		
		return User.builder()
				.username(userDto.getUsername()).password(passwordEncoder.encode(userDto.getPassword()))
				.roles(userDto.getRoles().toArray(new String[0])).build();
	}

}
