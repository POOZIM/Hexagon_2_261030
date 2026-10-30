package com.hexagon.springmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.HrRole;

@Mapper
public interface HrRoleMapper {
	List<HrRole> selectByAccountId(int accountId);
}
