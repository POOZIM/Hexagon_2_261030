package com.hexagon.springmvc.domain;

import lombok.Getter;
import lombok.Setter;

/*-------------------------------------
DTO - 파라미터 전달
Domain - DB 레코드를 담는 목적
----------------------------------------*/
@Getter
@Setter
public class Admin {
	private int adminId;
	private String loginId;
	private String password;
	private String name;
}
