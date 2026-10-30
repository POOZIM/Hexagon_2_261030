package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Admin;

@Mapper
public interface AdminMapper {
	public Admin selectById(String loginId); 
}
