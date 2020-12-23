package com.hyxt.service.impl;
import com.hyxt.remote.Result;
import com.hyxt.vo.FansGoodsBehaviorResponseVo;
import java.util.Date;
import com.google.common.collect.Lists;

import static org.junit.Assert.*;

import com.hyxt.remote.RemotePage;
import com.hyxt.service.FansGoodsBehaviorStoregeRemoteService;
import com.hyxt.vo.FansGoodsBehaviorRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/27 15:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FansGoodsBehaviorStoregeRemoteServiceImplTest {

  @Autowired
  private FansGoodsBehaviorStoregeRemoteService fansGoodsBehaviorStoregeRemoteService;

  @Test
  public void findPage() {

    String shopId = "3ecca848-0357-40cc-8ca8-480357e0cc74";
    String hyId = "ceb4bdfd-02b9-4892-b4bd-fd02b9589285";
    String empId = "2c859e28-bcf8-4308-859e-28bcf8f308a5";


    RemotePage remotePage = new RemotePage();
    remotePage.setPageSize(4);
    remotePage.setCurrent(1);

//    FansGoodsBehaviorRequestVo fansGoodsBehaviorRequestVo = new FansGoodsBehaviorRequestVo();
//    fansGoodsBehaviorRequestVo.setShopId(shopId);
//    fansGoodsBehaviorRequestVo.setAppcode(1);
//    fansGoodsBehaviorRequestVo.setDataSource(2);
//    fansGoodsBehaviorRequestVo.setHyId(hyId);
//    fansGoodsBehaviorRequestVo.setEmpId(empId);
//    fansGoodsBehaviorRequestVo.setType(0);



    FansGoodsBehaviorRequestVo fansGoodsBehaviorRequestVo = new FansGoodsBehaviorRequestVo();
    fansGoodsBehaviorRequestVo.setShopId("3ecca848-0357-40cc-8ca8-480357e0cc74");
//    fansGoodsBehaviorRequestVo.setAppcode(1);
    DateTime now = DateTime.now();
    DateTime dateTime = now.minusHours(1);

    fansGoodsBehaviorRequestVo.setBeginDate(dateTime.toDate());
    fansGoodsBehaviorRequestVo.setEndDate(new Date());


    Result<RemotePage<FansGoodsBehaviorResponseVo>> page = fansGoodsBehaviorStoregeRemoteService
        .findPage(remotePage, fansGoodsBehaviorRequestVo);
    log.info("↓==pageInfo==↓\n{}",page);

  }


}