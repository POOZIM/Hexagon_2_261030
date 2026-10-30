package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.HrPosition;

@Mapper
public interface HrPositionMapper {
	HrPosition select(int hrPositionId);
}
