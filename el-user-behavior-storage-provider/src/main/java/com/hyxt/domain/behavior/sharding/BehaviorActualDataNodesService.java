package com.hyxt.domain.behavior.sharding;

import com.hyxt.domain.behavior.BehaviorConstant;
import com.hyxt.domain.behavior.repository.FansGoodsBehaviorRepository;
import com.hyxt.sharding.dynamic.actualdata.AbstractActualDataNodesService;
import com.hyxt.sharding.dynamic.actualdata.ActualDataNodesService;
import javax.sql.DataSource;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.stereotype.Service;

/**
 * 用户行为分片的实例数据的服务
 * @description:
 * @author: lrh
 * @date: 2020/11/23 14:02
 */
@Service("behaviorActualDataNodesService")
public class BehaviorActualDataNodesService extends AbstractActualDataNodesService implements
    ActualDataNodesService {

  public BehaviorActualDataNodesService(
      DataSource dataSource, FansGoodsBehaviorRepository fansGoodsBehaviorRepository) {
    super((ShardingDataSource) dataSource, BehaviorConstant.BEHAVIOR_LOGIC_TABLE_NAME, fansGoodsBehaviorRepository);
  }




}
