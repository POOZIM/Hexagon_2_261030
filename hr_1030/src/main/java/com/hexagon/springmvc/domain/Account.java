package com.hexagon.springmvc.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
	
	private int accountId;
	private Emp emp;//데이터베이스에서의 외래키는 java에서는 부모 클래스로 보유 해야함(객체지향이므로)
	private String loginId;
	private String password;
	private int enabled;
	private LocalDateTime createdAt;
	private LocalDateTime lastLoginAt;
	
	//한명의 사원 계정은 여러개의 롤(퍼미션 집합)을 보유할 수 있다
	private List<HrRole> roles;
}
