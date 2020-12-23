package com.hyxt;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 用户行为的存储服务
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/19 14:06
 */
@SpringBootApplication
@EnableApolloConfig
@EnableScheduling
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserBehaviorStorageApplication{

  public static void main(String[] args) {
    SpringApplication.run(UserBehaviorStorageApplication.class, args);
  }

}
