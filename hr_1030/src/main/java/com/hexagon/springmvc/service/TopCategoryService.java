package com.hexagon.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexagon.springmvc.mapper.TopCategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopCategoryService {

	private final TopCategoryMapper topCategoryMapper;
	
	//모든 카테고리 가져오기
	public List getTopCategoryList() {
		return topCategoryMapper.selectAll();
	}
}
