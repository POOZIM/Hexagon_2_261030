package com.hexagon.springmvc.dto.request;

import lombok.Getter;
import lombok.Setter;

//이 클래스의 목적은 로직을 작성하기 위함이 아니라, 오직 단순하게
//html로부터 전송되어오는 파라미터를 받는 목적뿐이다
@Getter
@Setter
public class NewsRequestDTO {
	private int subCategoryId;
	private String subject;
	private String author;
	private String detail;
}
