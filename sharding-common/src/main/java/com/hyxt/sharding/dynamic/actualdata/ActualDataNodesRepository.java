package com.hyxt.sharding.dynamic.actualdata;

import java.util.List;

/**
 * 获取分库分表的实例接口
 * @description:
 * @author: lrh
 * @date: 2020/11/17 11:20
 */
public interface ActualDataNodesRepository {

  List<String> getActualTables();

  void createActualTable(String tableName);

}
