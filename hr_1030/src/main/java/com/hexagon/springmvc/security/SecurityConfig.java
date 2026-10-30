package com.hexagon.springmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration//로직용이 아닌 스프링 시큐리티 관련 설정 클래스임을 명시
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	private final LoginSuccessHandler loginSuccessHandler;//자동 주입  받자!!
	private final LoginFailureHandler loginfailureHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())//공부 목적상 csrf 설정은 생략한다
			
			.authorizeHttpRequests( auth ->
				auth.anyRequest().authenticated() //이 애플리케이션으로 들어오는 모든 요청은 인증이 적용되어 있다
																	//따라서 로그인하지 않은 요청은 로그인 폼으로 보내짐
			) 
			.formLogin( form -> form 
					.loginPage("/loginform")//개발자가 정의한 로그인 폼(커스텀 폼) 주소설정
					.loginProcessingUrl("/login")//UsertnamePasswordAuthenticationFilter를 어떤 주소로 만날지 결정
					.usernameParameter("accountId")//UsernamePasswordAuthenticationFilter가 인식할 아이디 파라미터명 설정(로그인 아이디어 파라미터 용)
					.passwordParameter("pwd")//UsernamePasswordAuthenticationFilter가 인식할 비밀번호 파라미터명 설정(패스원드 파라미터용)
					.successHandler(loginSuccessHandler)
					.failureHandler(loginfailureHandler) 
					.permitAll()
			);
		return http.build();
	}
	
	//우리 대신 비밀번호 검증해주는 빈 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
