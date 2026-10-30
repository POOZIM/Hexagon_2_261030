package com.hexagon.springmvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Permission;

@Mapper
public interface PermissionMapper {
	List<Permission> selectByHrRoleId(int hrRoleId);
}
