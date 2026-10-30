package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Job;

@Mapper
public interface JobMapper {
	Job select(int jobId);
}
