package com.hexagon.springmvc.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexagon.springmvc.domain.TopCategory;
import com.hexagon.springmvc.service.TopCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RestTopCategoryController {

	private final TopCategoryService topCategoryService;
	
	@GetMapping("/api/topcategories")
	public ResponseEntity<List<TopCategory>> getTopList(){
		
		
		List<TopCategory> topList= topCategoryService.getTopCategoryList();//3단계 일 시키기
		
		for(TopCategory top : topList) {
			log.debug("top_name is {}", top.getTopName());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(topList);
	}
}
