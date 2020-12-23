package com.hyxt.sharding.configuration;

import com.hyxt.sharding.ShardingInitialization;
import com.hyxt.sharding.dynamic.datasource.DruidSettings;
import com.hyxt.sharding.dynamic.datasource.DynamicDatasourceService;
import com.hyxt.sharding.rule.TableShardingRuleConfigurationFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分库分表自动配
 * @description:
 * @author: lrh
 * @date: 2020/11/19 19:06
 */
@Configuration
public class ShardingDatasourceConfiguration implements ApplicationContextAware {

  private DynamicDatasourceService dynamicDatasourceServices;

  @Bean
  @ConditionalOnMissingBean(value = {TableShardingRuleConfigurationFactory.class})
  public TableShardingRuleConfigurationFactory tableRuleConfigurationFactory() {
    return new TableShardingRuleConfigurationFactory();
  }

  @Bean
  @ConfigurationProperties(prefix = "druid.dataSource")
  @ConditionalOnMissingBean
  public DruidSettings druidSettings() {
    return new DruidSettings();
  }

  @Bean
  @ConditionalOnMissingBean(value = {DataSource.class})
  public DataSource shardingDataSource(TableShardingRuleConfigurationFactory tableRuleConfigurationFactory) throws SQLException {
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    List<TableRuleConfiguration> tableRuleServiceList = tableRuleConfigurationFactory
        .getTableRuleList();
    shardingRuleConfig.getTableRuleConfigs().addAll(tableRuleServiceList);

    return ShardingDataSourceFactory
        .createDataSource(dynamicDatasourceServices.getDataSourceMap(), shardingRuleConfig, new Properties());
  }

  @Bean
  @ConditionalOnMissingBean(value = {ShardingInitialization.class})
  public ShardingInitialization shardingInitialization() {
    return new ShardingInitialization();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
     this.dynamicDatasourceServices = applicationContext.getBean(DynamicDatasourceService.class);
  }
}
