package com.hyxt.service.sharding.impl;

import static com.hyxt.sharding.constant.ShardingDataSourceConstant.DEFAULT_SHARDING_DATA_SOURCE_NAME;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.hyxt.sharding.dynamic.datasource.DruidSettings;
import com.hyxt.sharding.dynamic.datasource.DynamicDatasourceService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.stereotype.Service;

/**
 * 分片的动态数据源获取服务
 * @description:
 * @author: lrh
 * @date: 2020/11/19 20:07
 */
@Service
public class DynamicDatasourceServiceImpl implements DynamicDatasourceService {

  private DruidSettings ds;

  public DynamicDatasourceServiceImpl(DruidSettings ds) {
    this.ds = ds;
  }

  @Override
  public Map<String, DataSource> getDataSourceMap() throws SQLException {
    HashMap<String, DataSource> dataSourceMap = Maps.newHashMap();
    DataSource dataSource = dataSource(DEFAULT_SHARDING_DATA_SOURCE_NAME);
    dataSourceMap.put(DEFAULT_SHARDING_DATA_SOURCE_NAME, dataSource);
    return dataSourceMap;
  }

  private DataSource dataSource(String dsName) throws SQLException {
    DruidDataSource druidDataSource = new DruidDataSource(false);
    druidDataSource.setName(dsName);
    druidDataSource.setUsername(ds.getUsername());
    druidDataSource.setUrl(ds.getUrl());
    druidDataSource.setPassword(ds.getPassword());
    druidDataSource.setFilters(ds.getFilters());
    druidDataSource.setMaxActive(ds.getMaxActive());
    druidDataSource.setInitialSize(ds.getInitialSize());
    druidDataSource.setMaxWait(ds.getMaxWait());
    druidDataSource.setMinIdle(ds.getMinIdle());
    druidDataSource.setTimeBetweenEvictionRunsMillis(ds.getTimeBetweenEvictionRunsMillis());
    druidDataSource.setMinEvictableIdleTimeMillis(ds.getMinEvictableIdleTimeMillis());
    druidDataSource.setValidationQuery(ds.getValidationQuery());
    druidDataSource.setTestWhileIdle(ds.isTestWhileIdle());
    druidDataSource.setTestOnBorrow(ds.isTestOnBorrow());
    druidDataSource.setTestOnReturn(ds.isTestOnReturn());
    druidDataSource.setPoolPreparedStatements(ds.isPoolPreparedStatements());
    druidDataSource.setMaxOpenPreparedStatements(ds.getMaxOpenPreparedStatements());
    return druidDataSource;
  }
}
