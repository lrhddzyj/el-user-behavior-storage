package com.hyxt.sharding;

import com.hyxt.sharding.dynamic.actualdata.ActualDataNodesService;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 分片应用启动后的自动刷新节点启动器
 * @description:
 * @author: lrh
 * @date: 2020/11/20 14:03
 */
@Slf4j
public class ShardingInitialization implements ApplicationRunner, ApplicationContextAware{

  private ApplicationContext applicationContext;


  @Override
  public void run(ApplicationArguments applicationArguments){
    Map<String, ActualDataNodesService> actualDataNodesServiceMap = applicationContext
        .getBeansOfType(ActualDataNodesService.class);
    for (Entry<String, ActualDataNodesService> stringActualDataNodesServiceEntry : actualDataNodesServiceMap
        .entrySet()) {
      ActualDataNodesService service = stringActualDataNodesServiceEntry.getValue();
      service.refresh();
    }

  }


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
