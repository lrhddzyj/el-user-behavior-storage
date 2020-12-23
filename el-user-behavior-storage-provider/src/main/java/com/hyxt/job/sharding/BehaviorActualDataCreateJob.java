package com.hyxt.job.sharding;

import com.hyxt.domain.behavior.service.FansGoodsBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用户行为分表数据实例
 * 每个月的最后一天执行 创建表任务
 * @description:
 * @author: lrh
 * @date: 2020/11/24 10:06
 */
@Component
@Slf4j
public class BehaviorActualDataCreateJob {

  private FansGoodsBehaviorService fansGoodsBehaviorService;

  public BehaviorActualDataCreateJob(
      FansGoodsBehaviorService fansGoodsBehaviorService) {
    this.fansGoodsBehaviorService = fansGoodsBehaviorService;
  }

  @Scheduled(cron = "* * 1 L * ? " )
  public void execute() {
    fansGoodsBehaviorService.createBehaviorTable();
  }
}
