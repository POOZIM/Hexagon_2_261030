package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Department;

@Mapper
public interface DepartmentMapper {
	Department select(int departmentId);
}
