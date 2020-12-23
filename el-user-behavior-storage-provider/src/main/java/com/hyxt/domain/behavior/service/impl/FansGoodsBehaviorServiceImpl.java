package com.hyxt.domain.behavior.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hyxt.domain.behavior.BehaviorConstant;
import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import com.hyxt.domain.behavior.repository.FansGoodsBehaviorRepository;
import com.hyxt.domain.behavior.repository.query.BehaviorQueryVo;
import com.hyxt.domain.behavior.service.FansGoodsBehaviorService;
import com.hyxt.sharding.component.SnowFlakeIdComponent;
import com.hyxt.sharding.util.YearMonthUtils;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 粉丝行为的服务
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/23 11:24
 */
@Service
public class FansGoodsBehaviorServiceImpl implements FansGoodsBehaviorService {

  private FansGoodsBehaviorRepository fansGoodsBehaviorRepository;

  private SnowFlakeIdComponent idComponent;

  public FansGoodsBehaviorServiceImpl(
      FansGoodsBehaviorRepository fansGoodsBehaviorRepository,
      SnowFlakeIdComponent idComponent) {
    this.fansGoodsBehaviorRepository = fansGoodsBehaviorRepository;
    this.idComponent = idComponent;
  }

  @Override
  public void save(FansGoodsBehavior fansGoodsBehavior) {
    fansGoodsBehavior.setId(Long.toString(idComponent.nextId()));
    fansGoodsBehaviorRepository.save(fansGoodsBehavior);
  }

  @Override
  public void updateViewStatusAndTime(String id,Integer appcode, Integer viewStatus, Integer liveTime) {
    fansGoodsBehaviorRepository.updateViewStatusAndTime(id,appcode, viewStatus, liveTime);
  }

  @Override
  public Page<FansGoodsBehavior> findPage(Page page, BehaviorQueryVo behaviorQueryVo) {
    return fansGoodsBehaviorRepository.findPage(page, behaviorQueryVo);
  }

  @Override
  public FansGoodsBehavior findByUUID(String uuid,Integer appcode) {
    List<FansGoodsBehavior> byUUIDResult = fansGoodsBehaviorRepository.findByUUID(uuid,appcode);
    if (CollectionUtils.isEmpty(byUUIDResult)) {
      return null;
    }
    return byUUIDResult.get(0);
  }

  @Override
  public void createBehaviorTable() {
    DateTime now = DateTime.now();
    DateTime afterSixMonthDate = now.plusMonths(6);
    String beginYearMonth = YearMonthUtils.formatYearMonthDate(now.toDate());
    String endYearMonth = YearMonthUtils.formatYearMonthDate(afterSixMonthDate.toDate());
    List<String> yearMonthNodes = YearMonthUtils.getYearMonthNodes(beginYearMonth, endYearMonth);
    for (String yearMonthNode : yearMonthNodes) {
      String tableName = BehaviorConstant.BEHAVIOR_LOGIC_TABLE_NAME + "_" + yearMonthNode;
      fansGoodsBehaviorRepository.createActualTable(tableName);
    }
  }
}
