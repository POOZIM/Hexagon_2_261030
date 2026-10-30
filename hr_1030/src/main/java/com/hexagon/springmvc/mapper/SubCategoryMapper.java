package com.hexagon.springmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubCategoryMapper {
	public List selectByTopCategoryId(int topcategoryId);

}
