package com.hexagon.springmvc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hexagon.springmvc.domain.News;

@Mapper
public interface NewsMapper {
	
	int insert(News news);
	
}
