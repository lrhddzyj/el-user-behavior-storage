package com.hyxt.repository.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hyxt.domain.behavior.BehaviorConstant;
import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import com.hyxt.domain.behavior.repository.FansGoodsBehaviorRepository;
import com.hyxt.domain.behavior.repository.query.BehaviorQueryVo;
import com.hyxt.repository.mapper.FansGoodsBehaviorMapper;
import com.hyxt.sharding.dynamic.datasource.DruidSettings;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 粉丝行为的资源服务
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/23 11:27
 */
@Repository
public class FansGoodsBehaviorRepositoryImpl implements FansGoodsBehaviorRepository {

  private FansGoodsBehaviorMapper fansGoodsBehaviorMapper;

  private DruidSettings druidSettings;

  public FansGoodsBehaviorRepositoryImpl(
      FansGoodsBehaviorMapper fansGoodsBehaviorMapper,
      DruidSettings druidSettings) {
    this.fansGoodsBehaviorMapper = fansGoodsBehaviorMapper;
    this.druidSettings = druidSettings;
  }

  @Override
  public void save(FansGoodsBehavior fansGoodsBehavior) {
    fansGoodsBehaviorMapper.save(fansGoodsBehavior);
  }

  @Override
  public Page<FansGoodsBehavior> findPage(Page page, BehaviorQueryVo behaviorQueryVo) {
    return page.setRecords(fansGoodsBehaviorMapper.findPage(page, behaviorQueryVo));
  }

  @Override
  public List<FansGoodsBehavior> findByUUID(String uuid,Integer appcode) {
    return fansGoodsBehaviorMapper.findByUUID(uuid,appcode);
  }

  @Override
  public void updateViewStatusAndTime(String id,Integer appcode, Integer viewStatus, Integer liveTime) {
    fansGoodsBehaviorMapper.updateViewStatusAndTime(id,appcode, viewStatus, liveTime);
  }

  @Override
  public List<String> getActualTables() {
    return fansGoodsBehaviorMapper.findTables(druidSettings.getName(),
        BehaviorConstant.BEHAVIOR_LOGIC_TABLE_NAME + "%");
  }

  @Override
  public void createActualTable(String tableName) {
    fansGoodsBehaviorMapper.createTable(tableName);
  }
}
