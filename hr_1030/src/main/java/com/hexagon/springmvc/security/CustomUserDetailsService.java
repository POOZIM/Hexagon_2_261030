package com.hexagon.springmvc.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexagon.springmvc.domain.Account;
import com.hexagon.springmvc.mapper.AccountMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*---------------------------------------------------------------------
스프링 시큐리티에서 DB연동이 시작되는 모델영역의 객체
개발자는 여기서 자신이 사용하는 DB 기술에 적한 코드를 작성해야함
JDBC - DAO 사용
MyBatis - Mapper 인터페이스 사용
Hibernate - 하이버네이트 Repository 사용
JPA - JPARepository
-----------------------------------------------------------------------*/
@Slf4j
@Service // 우리의경우 RootConfig 클래스 차원에서 빈 자동 검색 등록을 완료함
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final AccountMapper accountMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		// Account 테이블에 있는 회원 가져오기(오직 로그인 아이디를 통해 가져온다), 추후 비번 검증은 시큐리티 PasswordEncoder가 알아서함
		Account account = accountMapper.selectById(username);
		
		return new AccountDetails(account);
	}
}
