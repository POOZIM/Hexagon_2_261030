package com.hexagon.springmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Salary;

@Mapper
public interface SalaryMapper {

    // 급여 목록 조회
    List<Salary> selectAll();
}