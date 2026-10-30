package com.hexagon.springmvc.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hexagon.springmvc.domain.SubCategory;
import com.hexagon.springmvc.service.SubCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestSubCategoryController {
	
	private final SubCategoryService subCategoryService;
	
	@GetMapping("/api/subcategories/{topcategoryId}")
	public ResponseEntity<List<SubCategory>> getSubList(@PathVariable("topcategoryId") int topcategoryId){
		//3단계 일 시키기
		List<SubCategory> subList=subCategoryService.getListByTopId(topcategoryId);
		
		return ResponseEntity.status(HttpStatus.OK).body(subList);
	}
}
