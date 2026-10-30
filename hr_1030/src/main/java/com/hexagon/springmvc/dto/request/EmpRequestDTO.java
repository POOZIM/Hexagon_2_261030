package com.hexagon.springmvc.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

//데이터베이스의 컬럼과는 상관없는 오직 html폼 양식에서 넘어온 파라미터들을 받기 위한 dummy dto 임
//따라서 멤버변수명은 html의 ui 컴포넌트의 name 과 일치 해야함
@Getter
@Setter
public class EmpRequestDTO {
	
	@NotBlank(message = "제목은 필수임")
	@Size(max=6, message = "제목은 6자이내여야함")
	
	private String name;
	private int age;
	private String email;
	private float height;
	private float weight;
	private String bloodType;
	private int empId;
}
