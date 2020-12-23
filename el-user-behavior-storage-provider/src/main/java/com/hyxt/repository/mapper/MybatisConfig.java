package com.hyxt.repository.mapper;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置
 * @description:
 * @author: lrh
 * @date: 2020/11/19 17:10
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value = "com.hyxt.repository.mapper")
public class MybatisConfig {

  /**
   * 分页插件
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }


}

