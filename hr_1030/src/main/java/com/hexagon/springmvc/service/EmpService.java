package com.hexagon.springmvc.service;

import com.hexagon.springmvc.controller.EmpController;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexagon.springmvc.common.web.Pager;
import com.hexagon.springmvc.config.exception.BusinessException;
import com.hexagon.springmvc.domain.Bio;
import com.hexagon.springmvc.domain.Emp;
import com.hexagon.springmvc.dto.request.EmpRequestDTO;
import com.hexagon.springmvc.mapper.BioMapper;
import com.hexagon.springmvc.mapper.EmpMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*----------------------------------------------------------------------------------------------------------------------
 	사원등록이라는 하나의 업무는 사실, emp 와 bio 의 각각의 등록이라는 세부업무로 이루어져 있다
 	따라서 emp에는 등록이 성공이 되고, bio에는 등록이 실패될 경우 트랜잭션 처리까지 해야한다
 	이러한 DB 분야의 전문 업무를 만일 서비스 영역이 존재하지 않는다면, Controller가 서비스 대신 처리해야 하는 상황이 발생한다
 	즉 결론) 컨트롤러는 모델 영역의 자세한 업무를 모르게 해야 한다
 	해결책) 서비스 영역을 두어야 한다
 ------------------------------------------------------------------------------------------------------------------------*/
@Service
@RequiredArgsConstructor //lombok 의 기능중 하나로써, 현재 클래스의 멤버변수를 매개변수로 한
										//생성자를 자동으로 정의해줌
@Slf4j
@Transactional //이 서비스 객체가 트랜잭션을 처리할 수 있다
public class EmpService {
	//Mybatis의 인터페이스를 사용할 경우, DAO는 필요 없음
	private final EmpMapper empMapper;
	private final BioMapper bioMapper;
	//final을 명시한 이유는? 멤버변수에 final을 명시할 경우, 반드시 null이 아닌 값이 들어있어야
	//현재 클래스가 컴파일되기 때문에, 실수로 인한 null을 방지하기 위한 설정(안전장치)
	/*-------------------------------------------------------------------------------------
	 * @RequiredArgsConstructor 에 의해 아래와 같이 생성자를 자동으로 정의
	 * 스프링이 관리하는 빈을 주입 받기 위함(즉 DI때문에)
	 * public EmpService(EmpMapper empMapper){
	 * 
	 * }
	 * -------------------------------------------------------------------------------------*/
	public void regist(EmpRequestDTO empRequestDTO) {
		log.debug("서비스의 insert() 호출");
		
		//MYbatis 의 Mapper 인터페이스가 DTO가 아닌 도메인을 사용하기 때문에
		//DTO안의 데이터를 도메인 객체로 옮겨 담자
		Emp emp = new Emp();
		emp.setEname(empRequestDTO.getName());//사원명
		emp.setAge(empRequestDTO.getAge());//나이
		emp.setEmail(empRequestDTO.getEmail());//이메일
		
		/*---------------------------------------------------------------------------------------------------------------------
		 * 사원등록이라는 업무는 (emp+bio)가 모두 성송해야 전체를 성공으로 처리할 수 있는 트랜잭션 상황의 업무
		 * 따라서 bio까지 모두 제대로 들어가면 DML을 확정직고, 둘 중 하나라도 실패하면 rollback 철리할 것임
		 * 전통적으로 트랜잭션은 개발자가 지접 rollback, commit 을 명시했으나 너무 애플리케이션 전반적으로 트랜잭션 코드를 작성하면 
		 * 코드가 너무 가독성이 떨어지고 코드 중복이 발생함 그래서 스프링에서는 개발자가 트랜잭션을 별도로 처리하지 않음
		 * 단, 개발자는 실패할 경우 예외만 던지면 된다
		 *---------------------------------------------------------------------------------------------------------------------- */
		int result = empMapper.insert(emp);//트랜잭션을 이루는 세부업무 중 1/2 
		log.debug("emp 등록 결과는 {}", result);
		
		if(result<1) {//실패라면
			//개발자가 일부러 예외를 발생시키기
			throw new BusinessException("emp 등록실패");  
		}
		
		Bio bio = new Bio();
		bio.setHeight(empRequestDTO.getHeight());//키
		bio.setWeight(empRequestDTO.getWeight());//몸무게
		bio.setBloodType(empRequestDTO.getBloodType());//혈액형
		bio.setEmp(emp);//<--방금 insert된 사원의 emp_id 값을 대입
		
		result = bioMapper.insert(bio);//트랜잭션을 이루는 세부업무 중 2/2
		//트랜잭션의 세부 업무들 중 하나라도 실패하는 업무가 있다면, 스프링에게 에러가 났음을 알려줘야하는데
		//그 수단이 바로 java의 예외를 발생시키는 것이다
		//result=0;//조작
		
		if(result<1) {
			throw new BusinessException("사원의 신체 정보 등록 실패");//RuntimeException은 개발자가 별도로 매서드 우측에 throw 처리하지 않아도
																							//자동으로 이 메서드를 호출한 곳에 예회를 발생 시킨다
		}
		
		log.debug("bio 등록 결과는 {}", result);
	}//emp+bio 모두 insert 하는 업무
	
	//사원 목록 가져오기
	public List getEmpList(Pager pager) {
		
		//이 시점에 게시물을 가져오게 되므로, 기존 jsp가 담당하는 페이징 처리를 Pager 가 담당   
		//하게되므로, 여기서 Pager 에게 페이징 처리를 맡겨야 한다..따라서 Pager 에게   
		//필요한 정보는 다 넘겨야 한다..   
		List empList = empMapper.selectAll(pager);//게시판 목록을 가져오는 시점!
		int totalCount = empMapper.selectTotalCount();
		
		//이미 mybatis 에 offset, pageSize를 넘기고 온 Pager 한테 추가적으로
		//select 문으로 가져온 List 등의 정보를 넘겨서 페이징 계산을 또 맡김
		pager.setTotalCount(empList.size());
		pager.setList(empList);
		//pager.init(request); 여기서 원래 하면 더 개념상 쉽겠으나, 하지 않은 이유는 서비스는 모델 영역이므로 컨트롤러와
									//결합도가 강하면 안됨 즉 컨트롤러에서 사용되는 HttpServletRequest를 쓰면 안된다
		
		return empList;
	}
	
	//사원 한명 가져오기
	public Emp getDetail(int empId) {
		
		return empMapper.select(empId);
	}
}
