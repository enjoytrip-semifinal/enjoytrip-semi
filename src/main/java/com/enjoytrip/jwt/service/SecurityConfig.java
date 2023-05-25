package com.enjoytrip.jwt.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
//	private final LogoutHandler logoutHandler;
	
	/**
	 * httpBasic().disable().csrf().disable(): rest api이므로 basic auth 및 csrf 보안을 사용하지 않는다는 설정
	 *  sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS): JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
	 *  antMatchers().permitAll(): 해당 API에 대해서는 모든 요청을 허가한다는 설정
	 *  antMatchers().hasRole("USER"): USER 권한이 있어야 요청할 수 있다는 설정
	 *  anyRequest().authenticated(): 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
	 *  addFilterBefore(new JwtAUthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class): JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정
	 *  passwordEncoder: JWT를 사용하기 위해서는 기본적으로 password encoder가 필요한데, 여기서는 Bycrypt encoder를 사용
	 */
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()	 
                .csrf().disable()
                .cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/exist/**", "/user/exist/**").permitAll()
                .antMatchers("/user/refresh-token", "/user/login", "/user/findid", "/user/findpw").permitAll()
                .antMatchers("/user/logout/**", "/user/modify/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/admin/**").hasRole("ADMIN")
                .antMatchers("/user/user/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/user/join/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/join/**").permitAll() 
                .antMatchers(HttpMethod.DELETE, "/user/join/**").hasAnyRole("USER", "ADMIN")

                .antMatchers("/notice/list/**").permitAll()
                .antMatchers("/notice/write/**", "/notice/delete/**", "/notice/modify/**").hasRole("ADMIN")
                
                .antMatchers("/board/list/**").permitAll()
                .antMatchers("/board/write/**", "/board/delete/**", "/board/modify/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/board/review/list/**").permitAll()
                .antMatchers("/board/review/write/**", "/board/review/delete/**", 
                		"/board/review/modify/**").hasAnyRole("USER", "ADMIN")
                
                .antMatchers("/file/download/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/hotplace/list/**").permitAll()
                .antMatchers("/hotplace/write/**", "/hotplace/update/**", "/hotplace/delete/**"
                		,"/hotplace/like/**", "/hotplace/hate/**").hasAnyRole("USER", "ADMIN")
                
                .antMatchers("/hotplace/reply/list/**").permitAll()
                .antMatchers("/hotplace/reply/write/**", "/hotplace/reply/modify/**"
                		, "/hotplace/reply/delete/**").hasAnyRole("USER", "ADMIN")
                
                .antMatchers("/itinerary/list/**").permitAll()
                .antMatchers("/itineray/write/**", "/itineray/delete/**", "/itineray/modify/**", "/itineray/detail/**").hasAnyRole("USER","ADMIN")
                
                .antMatchers("/itinerary/reply/list/**").permitAll()
                .antMatchers("/itineray/reply/write/**", "/itineray/reply/delete/**"
                		, "/itineray/reply/modify/**").hasAnyRole("USER","ADMIN")
                

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
         ;
        return http.build();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}



}
