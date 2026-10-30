package com.hexagon.springmvc.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//비밀번호 검증이 성공되면 UsernamePasswordAuthenticationToken 이 생성되고
//그 토큰이 UsernamePasswordAuthentication로 도착하고, 이 필터가
//로그인 성공했음을 저장(SecurityContext)한 직후 아래의 성공 핸들러를 ㄹ호출함
//이 클래스에서 개발자는 성고의 응답 정보를 만들어서 보내주면 됨
@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		response.setStatus(HttpServletResponse.SC_OK);//헤더에 들어감
		response.setContentType("application/json; charset=UTF-8");//마임타입 즉 응답 정보의 데이터 유형, 형식을 지정
		
		StringBuffer data = new StringBuffer();
		data.append("{");
		data.append("      \"success\" : true,");
		data.append("      \"message\": \"로그인 성공\",");
		data.append("      \"data\" : null");
		data.append("}");
		
		PrintWriter out = response.getWriter();
		out.print(data.toString());
		out.flush();
		
	}
}
