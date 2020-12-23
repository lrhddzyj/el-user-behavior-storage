package com.hyxt.domain.behavior.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import com.hyxt.domain.behavior.repository.query.BehaviorQueryVo;

/**
 * 用户行为的领域服务
 * @description:
 * @author: lrh
 * @date: 2020/11/23 10:47
 */
public interface FansGoodsBehaviorService {

  /**
   * 保存
   * @param fansGoodsBehavior
   */
  void save(FansGoodsBehavior fansGoodsBehavior);

  /**
   * 更新记录的查看状态以及停留时间
   * @param id
   * @param viewStatus
   * @param liveTime
   * @param appcode
   */
  void updateViewStatusAndTime(String id,Integer appcode, Integer viewStatus, Integer liveTime);

  /**
   * 分页查询
   *
   * @param page
   * @param behaviorQueryVo
   * @return
   */
  Page<FansGoodsBehavior> findPage(Page page, BehaviorQueryVo behaviorQueryVo);

  /**
   * 用UUID查询数据
   * @param uuid
   * @param appcode
   * @return
   */
  FansGoodsBehavior findByUUID(String uuid,Integer appcode);

  /**
   *
   * 动态创建表 一次创建6个月的表
   */
  void createBehaviorTable();
}
