package com.hexagon.springmvc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexagon.springmvc.domain.Account;
import com.hexagon.springmvc.domain.HrRole;
import com.hexagon.springmvc.domain.Permission;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

//로그인 후 그 결과를 가진 Account 는 우리가 정의한 것이므로, 스프링이 그 명칭을 알 수 없기 떄문에
//접근하여 비번 등을 추출해갈 수 없다 따라서 스프링에서 정해놓은 객체로 옮겨 놓아야 스프링이 패스워드를
//추출하여 PasswordEncoder 등을 이용할 수 있으며, 추후 권한 처리시 이 사용자가 보휴한 여러 권한도 스프링이 접근할 수 있다.
//Account ->AccountDetails로 옮기기
@Slf4j
@Getter
public class AccountDetails implements UserDetails{

	private Account account;
	
	//이 계정이 보유한 권한들을 모아놓을 컬렉션 선언(주로 List 씀)
	private List<GrantedAuthority> authorities;
	
	public AccountDetails(Account account) {
		// TODO Auto-generated constructor stub
		this.account=account;
		
		//authorities 라는 List에 바로 넣어도 되지만 개발자가 db에 중복된 권한을 실수로 넣어둘 경우 중복된 권한이 보유됨
		//걸러내기 위함 Set은 컬렉션 프레임웍중 순서엇는 집합을 표한 객체 비록 순서는 없지만 중복을 허용하지않음

		Set<GrantedAuthority> authSet=new LinkedHashSet<>();
		//List<HrRole> roles = account.getRoles();//이 계정이 보유한 Role들
		
		for(HrRole hrRole : account.getRoles()) {
			//현재 Role에 등록된 Permission 등 끄집어 내기
			//List<Permission> permissions = role.getPermissions();
			
			log.debug("현재 role={}", hrRole.getRoleCode());
			authSet.add(new SimpleGrantedAuthority(hrRole.getRoleCode()));
			
			for(Permission  permission: hrRole.getPermissions()) {
				log.debug("소속 permission은{}", permission.getPermissionCode());
				authSet.add(new SimpleGrantedAuthority(permission.getPermissionCode()));
			}
		}
		//모든 권한이 채워진 Set을 List로 변환하자!(여러가지 방법 중 List 구현체의 생성자의 매개변수로 Set을 넣으면 Set의 요소들이 List에 복사됨)
		authorities = new ArrayList<>(authSet);
	}
	
	//이 메서드를 통해서, 이 사원이 어떤 권한을 가지고 있는지를 저장해놓으면
	//보안처리가 가능해짐
	//이 메서드에서 반환된 권한들이 스프링 시큐리티에 적용되었으므로, 이 권한에 속하지 않은 접근이 있다면 스프링이 알아서 차단!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return account.getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return account.getEnabled()==1;
	}

}
