package com.hyxt.configuration;

import com.hyxt.sharding.component.SnowFlakeIdComponent;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分片相关的配置
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/19 20:04
 */
@Configuration
public class ShardingConfiguration {

  /**
   * id 生成器
   * @return
   */
  @Bean
  public SnowFlakeIdComponent snowFlakeIdComponent(){
    return new SnowFlakeIdComponent(1, RandomUtils.nextInt(0, 10));
  }

}
