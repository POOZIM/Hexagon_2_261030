package com.hexagon.springmvc.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Emp {
	private int empId;
	private String ename;
	private String email;
	private String phone;
	private LocalDateTime hiredate;
	private Department department;//fk 는 java 클래스로 보유
	private Job job;//fk 는 java 클래스로 보유
	private HrPosition hrPosition;//fk 는 java 클래스로 보유
	private String status;
	private int annualLeaveDays;
}
