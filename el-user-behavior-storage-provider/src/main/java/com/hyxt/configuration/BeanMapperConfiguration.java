package com.hyxt.configuration;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对象映射相关的工具
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/24 10:50
 */
@Configuration
public class BeanMapperConfiguration {

  @Bean
  public MapperFactory mapperFactory() {
    return new DefaultMapperFactory.Builder().build();
  }

  @Bean("defaultMapperFacade")
  public MapperFacade mapperFacade() {
    return mapperFactory().getMapperFacade();
  }

}
