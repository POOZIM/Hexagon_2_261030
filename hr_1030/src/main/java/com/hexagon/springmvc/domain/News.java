package com.hexagon.springmvc.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {
	private int news_id;                                    
	private String title;                                          
	private String writer;                                       
	private String content;                                     
	private LocalDateTime created_at;                                
	private int hit;                                            
	private SubCategory subcategory;//부모를 숫자따위가 아닌 부모자료형으로 보유!                          
}
