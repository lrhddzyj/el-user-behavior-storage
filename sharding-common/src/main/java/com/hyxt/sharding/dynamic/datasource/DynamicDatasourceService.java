package com.hyxt.sharding.dynamic.datasource;

import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;

/**
 * 动态数据源的配置接口
 * @description:
 * @author: lrh
 * @date: 2020/11/19 20:00
 */
public interface DynamicDatasourceService {

  Map<String, DataSource> getDataSourceMap() throws SQLException;

}
