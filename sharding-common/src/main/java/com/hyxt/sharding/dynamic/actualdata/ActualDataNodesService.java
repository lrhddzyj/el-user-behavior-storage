package com.hyxt.sharding.dynamic.actualdata;

/**
 * 动态实例节点刷新
 * @description:
 * @author: lrh
 * @date: 2020/11/17 10:33
 */
public interface ActualDataNodesService {

  /**
   * 刷新逻辑表的动态实例
   */
  void refresh();

}
