package com.hexagon.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AjaxController {
	
	@GetMapping("/spa/main")
	public String getMain() {
		
		return "spa/index";
	}
	
	@GetMapping("/ajax/registform")
	public String getForm() {
		
		return "ajax/search";
	}
	
	@PostMapping("ajax/getword")
	public String getWordList() {
		log.debug("검색 키워드 요청 받음");
		return "ajax/search";
	}
	
	@GetMapping("/ajax/test")
	@ResponseBody//이 어노테이션이 설정되면, jsp 매핑을 하지 말고, 문자열 그대로 응답정보로 사용하라!
	public String getTest() {
		
		log.debug("ajax 요청 테스트");		
		
		//return 에 String을 넣으면, ViewResolver 에게 DispatcharServlet 이 맡겨버린다
		//따라서 return 되는 값이 jsp 매핑이 아닌 순수 데이터가 되려면 ViewResvler를 만나지 않도록 지정해야함
		
		return "hahaha";
	}
}
