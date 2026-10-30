package com.hexagon.springmvc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department {
	private int departmentId;
	private String departmentCode;
	private String departmentName;
	private int isActive;//오라클에서 1,0 이므로
}
