package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.Bio;

//mabatis의 쿼리문이 작선된 Mapper.xml를 대신 제어해주는 interface
@Mapper
public interface BioMapper {
	public int insert(Bio bio );
}
