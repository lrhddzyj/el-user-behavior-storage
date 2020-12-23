package com.hyxt.sharding.rule;

import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;

/**
 * 动态表规则的配置服务
 * @description:
 * @author: lrh
 * @date: 2020/11/19 19:19
 */
public interface TableRuleBuildService {

  TableRuleConfiguration build();

}
