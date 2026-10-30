package com.hexagon.springmvc.config.exception;

//개발자만의 업무용 예외를 정의할 수 있으며 이때는 강요하지 않는 예외의 최상위 자료형인 RuntimeException을
//상속받아 구현한다
public class BusinessException extends RuntimeException{
	
	//예외 객체에 넣을 메시지 처리, 원래 예회객체에 원하는 메시지를 심으려면 생성자를 통해서 가능한데
	//java 에서는 부모의 생성자는 자식에게 물려지지 않으므로
	//RuntimeException 에 메시지가 전달되려면 부모 생성자를 직접 호출해야함
	public BusinessException(String message) {
		super(message);
	}
	
}
