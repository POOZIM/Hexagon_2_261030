package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.hexagon.springmvc.domain.Emp;

@Mapper
public interface EmpMapper {
	Emp select(int empId);
}
