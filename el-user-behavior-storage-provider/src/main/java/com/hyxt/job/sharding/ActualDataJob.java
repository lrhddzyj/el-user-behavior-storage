package com.hyxt.job.sharding;

import com.hyxt.sharding.dynamic.actualdata.ActualDataNodesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 分表实例数据的job，非分布式的，每个实例都需要执行
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/24 10:06
 */
@Component
@Slf4j
public class ActualDataJob {

  private List<ActualDataNodesService> dataNodesServiceList;

  public ActualDataJob(
      List<ActualDataNodesService> dataNodesServiceList) {
    this.dataNodesServiceList = dataNodesServiceList;
  }

  /**
   * 刷新所有分表的实例节点
   */
  @Scheduled(cron = "0 0 1 1/3 * ?")
  public void refresh() {
    for (ActualDataNodesService actualDataNodesService : dataNodesServiceList) {
      actualDataNodesService.refresh();
    }
  }


}
