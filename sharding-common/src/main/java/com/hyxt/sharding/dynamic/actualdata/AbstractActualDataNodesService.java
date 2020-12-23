package com.hyxt.sharding.dynamic.actualdata;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.springframework.util.ReflectionUtils;

/**
 * 动态实例刷新节点的抽象服务
 * @description:
 * @author: lrh
 * @date: 2020/11/17 10:35
 */
@Slf4j
public abstract class AbstractActualDataNodesService implements ActualDataNodesService {

  protected ShardingDataSource shardingDataSource;

  protected String logicTableName;

  protected ActualDataNodesRepository actualDataNodesRepository;

  public AbstractActualDataNodesService(
      ShardingDataSource shardingDataSource, String logicTableName,
      ActualDataNodesRepository actualDataNodesRepository) {
    this.shardingDataSource = shardingDataSource;
    this.logicTableName = logicTableName;
    this.actualDataNodesRepository = actualDataNodesRepository;
  }

  @Override
  public void refresh() {
    ShardingRuntimeContext runtimeContext = shardingDataSource.getRuntimeContext();
    ShardingRule shardingRule = runtimeContext.getRule();

    TableRule tableRule = shardingRule.getTableRule(logicTableName);

    List<DataNode> oldActualDataNodes = tableRule.getActualDataNodes();
    String dataSourceName = oldActualDataNodes.get(0).getDataSourceName();
    Set<DataNode> newNodes = getNewNodes(dataSourceName);
    if (CollectionUtils.isEmpty(newNodes)) {
      log.error(String.format("未找到逻辑表 %s 的表实例数据", logicTableName));
      return;
    }
    try {
      doRefresh(dataSourceName, tableRule, Lists.newArrayList(newNodes));
    } catch (Exception e) {
      throw new RuntimeException("刷新data nodes 失败");
    }
  }

  /**
   * 执行动态刷新
   */
  protected void doRefresh(String dataSourceName, TableRule tableRule, List<DataNode> newDataNodes) {
    Set<String> actualTables = Sets.newHashSet();
    Map<DataNode, Integer> dataNodeIndexMap = Maps.newHashMap();
    AtomicInteger index = new AtomicInteger(0);

    for (DataNode dataNode : newDataNodes) {
      actualTables.add(dataNode.getTableName());
      if (index.intValue() == 0) {
        dataNodeIndexMap.put(dataNode, 0);
      } else {
        dataNodeIndexMap.put(dataNode, index.intValue());
      }
      index.incrementAndGet();
    }


    Field actualDataNodes = ReflectionUtils.findField(TableRule.class, "actualDataNodes");
    ReflectionUtils.makeAccessible(actualDataNodes);
    ReflectionUtils.setField(actualDataNodes, tableRule, newDataNodes);

    Field actualTablesField = ReflectionUtils.findField(TableRule.class, "actualTables");
    ReflectionUtils.makeAccessible(actualTablesField);
    ReflectionUtils.setField(actualTablesField, tableRule, actualTables);

    Field dataNodeIndexMapField = ReflectionUtils.findField(TableRule.class, "dataNodeIndexMap");
    ReflectionUtils.makeAccessible(dataNodeIndexMapField);
    ReflectionUtils.setField(dataNodeIndexMapField, tableRule, dataNodeIndexMap);

    Map<String, Collection<String>> datasourceToTablesMap = Maps.newHashMap();
    datasourceToTablesMap.put(dataSourceName, actualTables);
    Field datasourceToTablesMapField = ReflectionUtils.findField(TableRule.class, "datasourceToTablesMap");
    ReflectionUtils.makeAccessible(datasourceToTablesMapField);
    ReflectionUtils.setField(datasourceToTablesMapField, tableRule, datasourceToTablesMap);
  }


  /**
   * 动态获取节点数据源
   *
   * @param dataSourceName
   * @return
   */
  protected  Set<DataNode> getNewNodes(final String dataSourceName){
    Set<DataNode> newDataNodes = Sets.newHashSet();
    List<String> tableNames = actualDataNodesRepository.getActualTables();

    for (String tableName : tableNames) {
      final String dataNodeInfo = new StringBuilder().append(dataSourceName).append(".").append(tableName).toString();
      DataNode dataNode = new DataNode(dataNodeInfo);
      newDataNodes.add(dataNode);
    }
    return newDataNodes;

  }


}
