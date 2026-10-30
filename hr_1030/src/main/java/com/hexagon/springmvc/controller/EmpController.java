package com.hexagon.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hexagon.springmvc.common.web.Pager;
import com.hexagon.springmvc.config.exception.BusinessException;
import com.hexagon.springmvc.dto.request.EmpRequestDTO;
import com.hexagon.springmvc.service.EmpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//사원과 관련된 요청을 처리하는 하위 컨트롤러 
@Controller //  원래는  AppConfig 클래스에서 빈을 등록했지만, 스프링에서 이미 잘 알려진 특수한 빈들을 컴포넌트라 하며
//이 컴포넌트들은 어노테이션으로 자동 등록한다!! 
// @Controller, @Service, @Repository, @Component, @ControllerAdvice....
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/emp")// /emp로 시작하는 공통!
public class EmpController {
	
	private final EmpService empService;//RootConfig 에 등록되어 @ComponentScan의대상이 되는 컴포넌트 이므로 이미 메모리에 올라옴 현재 컨트롤러에 자동 주입이 완료 됨
	private final Pager pager;
	
	/*------------------------------- 
	사원 등록 폼 요청을 처리하는 메서드 
	----------------------------------*/ 	
	@GetMapping("/registform")
	public String getRegistForm() {

		return "emp/regist";
	}
	
	//사원 등록 요청을 처리하는 메서드
	@PostMapping("/regist")
	public String regist(@Valid @ModelAttribute EmpRequestDTO empRequestDTO) {
		//System.out.println(empRequestDTO.getName());
		//스프링은 .html 파라미터와 DTO의 멤버변수인 필드가 일치할 경우 자동 바인딩(묶어줌)
		
		log.debug("사원명은{}",empRequestDTO.getName());
		log.debug("나이는{}",empRequestDTO.getAge());
		log.debug("이메일은{}",empRequestDTO.getEmail());
		log.debug("키는{}",empRequestDTO.getHeight());
		log.debug("몸무게는{}",empRequestDTO.getWeight());
		log.debug("혈액형은{}",empRequestDTO.getBloodType());
		
		//컨트롤러가 모델 영역에 "사원가입"을 요청하는 시점
		//만일 서비스의 존재가 없다면 컨트롤러는 emp에 넣어도 주고 bio에도 넣어줘
		//즉 컨트롤러 영역에서는 모델계층에 일을 시킬때는 추상적으로 시켜야함 따라서 서비스의 존재는 필수
		//만일 없다면 계층이 컨트롤러의 영역과 서비스의 영역이 모호해짐
		empService.regist(empRequestDTO);
	
		//return "/emp/list";//forwording(포워딩)은 글 등록 후 사용하기엔는 부적절
		return "redirect:/emp/list";//현재 요청을 끊고, 클라이언트브라우저로 하여금 location.href="/emp/list" 를 이용하여 다시 접속함
	}
	
	//게시판 목록 요청처리
	@GetMapping("/list")
	public String getList(Model model, HttpServletRequest request) {//request에 직접 담아도 되지만 스프링에서는 결과를 model 객체를 통해 담는다
		
		/*
		 * Pager 의 역할
		 * 1) mybatis Mapper.xml 의 offset, pageSize 값을 넘기기 위한 목적
		 * 2) select 문 수행 후 반환된 List를 대상으로 분할 및 분석하는 목적
		 * */
		
		pager.init(request);//Pager 초기화 (계산식이 동작해야하므로)
		
		//3단계) 모델 영역에 일 시키기!
		List empList = empService.getEmpList(pager);
		
		//서비스 객체에 totalCount, List 를 주입했으므로, 다시 페이징 계산을 하기 위해 호출함
		pager.init(request);
		
		//4단계) jsp에 보여질 List 저장
		//model.addAttribute("empList", empList); service 에서 이미 Pager 에 이미 List 를 심었음
		model.addAttribute("pager", pager);

		log.debug("db에서 가져온 결과 리스트는 {}", empList);
		return "emp/list";
	}
	
	
	//현재 컨트롤어에서 발생하는 이벤트를 잡아내기 위한 어노테이션 선언
	//BindException 이란? HTML 폼 파라미터와 컨트롤러의 DTO가 제대로 바인딩 되지 못하였을 경우 Spring이 발생시키는 예외 객체
	@ExceptionHandler(BindException.class)
	public String handle(BindException e, Model model) {
		e.printStackTrace();//개발자를 위한 로그
		//여기서 jsp 출력을 담당하지 않는다. 컨트롤러이기 떄문에
		//View로 가져갈 내용이 있따면 결과 저장 후 포워딩
		//우리가 만든 프레임웍에서는 결과를 request 직접 저장 했으나 스프링MVC에서는
		//Model 이라는 객체에 결과를 저장하면 내부적으로 자동으로 request 객체에 저장이됨
		model.addAttribute("errorMessage","입력데이터가 올바르지 않음!!");
		//개발자가 redirect: 키워드를 명시하지 않으면 스프링은 디폴트가 forwording
		return "/error/common-error";
	}
	
	//현재 컨트롤러가 보유한 모든 메서드에서 발생하는 예외중 BusinessException 이 있다면 아래의 메서드가 낚아채서 개발자가 원하는
	//예외처리를 할 수 있따
	@ExceptionHandler(BusinessException.class)
	public String handle(BusinessException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "/error/common-error";
	}
	
	//위에서 명시한 비즈니스 예외, 이외의 예외들(시스템적) 및 기타 예외도 처리
	@ExceptionHandler(Exception.class)
	public String handle(Exception e, Model model) {
		model.addAttribute("errorMessage", "시스템 에러 발생.잠시후 다시 이용해 주세요");
		log.debug(e.getMessage());
		return "/error/common-error";
	}
	
}
