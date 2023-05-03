package com.enjoytrip.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan(basePackages = { "com.enjoytrip.**.mapper" })
public class WebMvcConfiguration implements WebMvcConfigurer {

}
  