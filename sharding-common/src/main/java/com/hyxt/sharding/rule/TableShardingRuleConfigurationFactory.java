package com.hyxt.sharding.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取tableShardingRuleConfiguration的工厂
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/19 19:10
 */
@Slf4j
public class TableShardingRuleConfigurationFactory implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  private List<TableRuleConfiguration> tableRuleList = new ArrayList<>();


  public List<TableRuleConfiguration> getTableRuleList() {
    tableRuleList.clear();
    Map<String, TableRuleBuildService> tableRuleMap = applicationContext
        .getBeansOfType(TableRuleBuildService.class);
    for (Entry<String, TableRuleBuildService> tableRuleServiceEntry : tableRuleMap.entrySet()) {
      TableRuleBuildService ruleService = tableRuleServiceEntry.getValue();
      tableRuleList.add(ruleService.build());
    }
    return tableRuleList;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
