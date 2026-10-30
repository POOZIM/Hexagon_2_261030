package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Account;

@Mapper
public interface AccountMapper {
	Account selectById(String loginId);
}
