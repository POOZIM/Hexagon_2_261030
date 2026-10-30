package com.hexagon.springmvc.service;

import org.springframework.stereotype.Service;

import com.hexagon.springmvc.domain.News;
import com.hexagon.springmvc.dto.request.NewsRequestDTO;
import com.hexagon.springmvc.mapper.NewsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {
	private final NewsMapper newsMapper;
	
	//뉴스 기사 등록
	//컨트롤러가 서비스에게 일 시킬때, 자신이 가진 DTO를 넘겨주면
	//모델 영역인 서비스는 DTO안의 대아토를 도메인 객체로 옮겨야 한다
	//이렇게 하면 html 에 컬럼명을 노출시키지 않을 수 있다
	public int regist(NewsRequestDTO newsRequestDTO) {
		
		News news = new News();
		
		newsMapper.insert(null);
		
		return 0;
	}
}
