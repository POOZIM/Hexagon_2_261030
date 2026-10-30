package com.hexagon.springmvc.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.hexagon.springmvc.service.AuthTestService;

import lombok.RequiredArgsConstructor;

// 우리가 스프링 시큐리티에 적용한 권한이 제대로 동작하는지 테스트 하기 위한 컨트롤러 
@RestController
@RequiredArgsConstructor
public class RestAuthController {
	private final AuthTestService authTestService;
	
	
	// 나의 정보 요청 처리 
	@GetMapping("/api/emps/{empId}")
	public String getMyInfo(@PathVariable("empId") int empId) {
		return authTestService.getEmp(empId);
	}
	
	
	// 권한 부여 요청 처리
	@GetMapping("/api/auth")
	public String addAuth() {
		return authTestService.addPermission();
	}
	
	// 권한이 없는 요청의 경우 스프링이 가로막으면서, 예외를 발생시키는데, 이 예외 객체가 
	// 바로 AccessDiniedException 이다!!
	@ExceptionHandler(AccessDeniedException.class)
	public String handle(AccessDeniedException e, HttpServletResponse response) {
		
		//HTTP통신은 원착을 지켜 헤더와 바디를 갸벌저거 재대로 구성
		
		//헤더에 성공 or 실패 정보를 넣어야 한다!!
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);//헤더에 실패의 HTTP status 코드를 심자!
		response.setContentType("application/json; charset=UTF-8");
		
		//바디에 전송할 데이터를 넣자!!(요청이 비동기일 경우 json으로 응답한다!)
		StringBuffer data = new StringBuffer();

		data.append("{");
		data.append("      \"success\" : false,");
		data.append("      \"message\": \"권한이 없습니다\",");
		data.append("      \"data\" : null");
		data.append("}");
		
		return data.toString();
	}
	
}
