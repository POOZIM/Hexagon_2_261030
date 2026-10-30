package com.hexagon.springmvc.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexagon.springmvc.domain.Admin;

import lombok.Getter;

//애플리케이션 마다  db상 아이디 , 패스워드 등의 컬럼명이 틀리기 때문에 스프링이 자동으로 인식할 수 없다.
//따라서 스프링 측에서 이미 정의해놓은 도메인 객체인 UserDetails 안에 우리의 Admin  도메인 객체의 정보를 대입해놓아야 한다
@Getter
public class AdminDetails implements UserDetails{
	
	//사용자 정보를 낱개로 보유하지 않기 위해 Admin 통째로 보유하자!!
	private Admin admin;

	public AdminDetails(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList(); //권한이 없더라도  null  로 놓으면 안되고, 그냥 객체는 존재시키되 한건도 없는 상태로 반환하면 됨..
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}

	//스프링 시큐리티에서 말하는 username  이란 로그인 아이디를 말한다!!	
	@Override
	public String getUsername() {
		return admin.getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}