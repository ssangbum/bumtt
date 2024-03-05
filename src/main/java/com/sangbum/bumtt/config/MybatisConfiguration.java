package com.sangbum.bumtt.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.sangbum.bumtt", annotationClass = Mapper.class)
public class MybatisConfiguration {
}
