package com.hyxt.domain.behavior.repository;

import com.baomidou.mybatisplus.plugins.Page;
import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import com.hyxt.domain.behavior.repository.query.BehaviorQueryVo;
import com.hyxt.sharding.dynamic.actualdata.ActualDataNodesRepository;
import java.util.List;

/**
 * 用户行为资源
 * @description:
 * @author: lrh
 * @date: 2020/11/23 10:46
 */
public interface FansGoodsBehaviorRepository extends ActualDataNodesRepository {

  void save(FansGoodsBehavior fansGoodsBehavior);

  Page<FansGoodsBehavior> findPage(Page page, BehaviorQueryVo behaviorQueryVo);

  List<FansGoodsBehavior> findByUUID(String uuid,Integer appcode);

  void updateViewStatusAndTime(String id,Integer appcode, Integer viewStatus, Integer liveTime);

}
