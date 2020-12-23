package com.hyxt.domain.behavior.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.hyxt.domain.behavior.repository.query.BehaviorQueryVo;
import java.util.Date;
import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FansGoodsBehaviorServiceTest {

  @Autowired
  private FansGoodsBehaviorService fansGoodsBehaviorService;

  @Test
  public void save() {
    FansGoodsBehavior fansGoodsBehavior = new FansGoodsBehavior();

    fansGoodsBehavior.setShopId("1");
    fansGoodsBehavior.setStoreId("");
    fansGoodsBehavior.setEmpId("");
    fansGoodsBehavior.setHyId("");
    fansGoodsBehavior.setOpenId("");
    fansGoodsBehavior.setWxUnionId("");
    fansGoodsBehavior.setNickName("");
    fansGoodsBehavior.setPhoto("");
    fansGoodsBehavior.setGoodsId("");
    fansGoodsBehavior.setGoodsName("");
    fansGoodsBehavior.setGoodsUrl("");
    fansGoodsBehavior.setGoodsPicUrl("");
    fansGoodsBehavior.setViewStatus(0);
    fansGoodsBehavior.setIsBuy(false);
    fansGoodsBehavior.setIsKeep(false);
    fansGoodsBehavior.setLiveTime(0);
    fansGoodsBehavior.setViewDate("");
    fansGoodsBehavior.setViewTime("");
    fansGoodsBehavior.setCreateTime(new Date());
    fansGoodsBehavior.setDataSource(0);
    fansGoodsBehavior.setType(0);
    fansGoodsBehavior.setRedirectUrl("");
    fansGoodsBehavior.setActivityId("");
    fansGoodsBehavior.setAppcode(1);

    for (int i = 0; i < 10; i++) {
      fansGoodsBehavior.setUuid(UUID.randomUUID().toString());
      fansGoodsBehaviorService.save(fansGoodsBehavior);
    }
  }

  @Test
  public void findPage() {
    Page pageRequest = new Page();
    pageRequest.setSize(10);
    pageRequest.setCurrent(1);

    BehaviorQueryVo behaviorQueryVo = new BehaviorQueryVo();
    DateTime now = DateTime.now();
    DateTime dateTime = now.minusDays(30);
    Date endDate = now.toDate();
    Date  beginDate= dateTime.toDate();
    behaviorQueryVo.setBeginDate(beginDate);
    behaviorQueryVo.setEndDate(endDate);
    behaviorQueryVo.setShopId("3ecca848-0357-40cc-8ca8-480357e0cc74");
    Page<FansGoodsBehavior> page = fansGoodsBehaviorService.findPage(pageRequest, behaviorQueryVo);
    log.info("↓==pageInfo==↓\n{}",page);

  }

  @Test
  public void findPage2() {
    Page pageRequest = new Page();
    pageRequest.setSize(10);
    pageRequest.setCurrent(1);

    BehaviorQueryVo behaviorQueryVo = new BehaviorQueryVo();
    behaviorQueryVo.setShopId("3ecca848-0357-40cc-8ca8-480357e0cc74");
    behaviorQueryVo.setAppcode(1);
    behaviorQueryVo.setHyId("94a2986d-a059-46b0-a298-6da059d6b025");
    DateTime now = DateTime.now();
    DateTime dateTime = now.minusDays(30);
    Date endDate = now.toDate();
    Date  beginDate= dateTime.toDate();
    behaviorQueryVo.setBeginDate(beginDate);
    behaviorQueryVo.setEndDate(endDate);
    Page<FansGoodsBehavior> page = fansGoodsBehaviorService.findPage(pageRequest, behaviorQueryVo);
    log.info("↓==pageInfo==↓\n{}",page);

  }


  @Test
  public void createBehaviorTable() {
    fansGoodsBehaviorService.createBehaviorTable();
  }

  @Test
  public void findByUUID() {
    FansGoodsBehavior behavior = fansGoodsBehaviorService
        .findByUUID("59949c80-a790-4050-a8b1-37b551e3cc34",1);
    FansGoodsBehavior behavior1 = fansGoodsBehaviorService
        .findByUUID("59949c80-a790-4050-a8b1-37b551e3cc34",0);
    log.info("0:{}", behavior);
    log.info("1:{}", behavior1);

  }

  @Test
  public void updateViewStatusAndTime() {
    fansGoodsBehaviorService.updateViewStatusAndTime("529005387668656128",0, 1, 100);
    fansGoodsBehaviorService.updateViewStatusAndTime("529005387668656128",1, 1, 100);
    fansGoodsBehaviorService.updateViewStatusAndTime("529005605755715584",1, 1, 100);
    fansGoodsBehaviorService.updateViewStatusAndTime("529005605755715584",0, 1, 100);
  }
}