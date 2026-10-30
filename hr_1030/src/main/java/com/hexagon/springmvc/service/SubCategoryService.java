package com.hexagon.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexagon.springmvc.mapper.SubCategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
	
	private final SubCategoryMapper subCategoryMapper;
	
	public List getListByTopId(int topcategoryId) {
		return subCategoryMapper.selectByTopCategoryId(topcategoryId);
	}
}
