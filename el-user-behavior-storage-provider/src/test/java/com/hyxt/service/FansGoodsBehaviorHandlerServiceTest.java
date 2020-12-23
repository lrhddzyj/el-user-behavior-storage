package com.hyxt.service;

import static org.junit.Assert.*;

import com.hyxt.dto.FansGoodsBehaviorMessageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/26 14:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FansGoodsBehaviorHandlerServiceTest {

  @Autowired
  FansGoodsBehaviorHandlerService fansGoodsBehaviorHandlerService;

  @Test
  public void handler() {
    FansGoodsBehaviorMessageDTO fansGoodsBehaviorDTO = new FansGoodsBehaviorMessageDTO();
    fansGoodsBehaviorDTO.setId("89998989123123");
    fansGoodsBehaviorDTO.setShopId("2222222222");
    fansGoodsBehaviorDTO.setStoreId("");
    fansGoodsBehaviorDTO.setEmpId("");
    fansGoodsBehaviorDTO.setHyId("");
    fansGoodsBehaviorDTO.setOpenId("");
    fansGoodsBehaviorDTO.setWxUnionId("");
    fansGoodsBehaviorDTO.setNickName("");
    fansGoodsBehaviorDTO.setPhoto("");
    fansGoodsBehaviorDTO.setGoodsId("");
    fansGoodsBehaviorDTO.setGoodsName("");
    fansGoodsBehaviorDTO.setGoodsUrl("");
    fansGoodsBehaviorDTO.setGoodsPicUrl("");
    fansGoodsBehaviorDTO.setViewStatus(0);
    fansGoodsBehaviorDTO.setIsBuy(false);
    fansGoodsBehaviorDTO.setIsKeep(false);
    fansGoodsBehaviorDTO.setLiveTime(0);
    fansGoodsBehaviorDTO.setViewDate("");
    fansGoodsBehaviorDTO.setViewTime("");
    fansGoodsBehaviorDTO.setViewCount(0);
    fansGoodsBehaviorDTO.setLiveTimeAvg(0);
    fansGoodsBehaviorDTO.setCreateTime("");
    fansGoodsBehaviorDTO.setDataSource(0);
    fansGoodsBehaviorDTO.setType(0);
    fansGoodsBehaviorDTO.setAppSource(1);
    fansGoodsBehaviorDTO.setRedirectUrl("");
    fansGoodsBehaviorHandlerService.handler(fansGoodsBehaviorDTO);
  }
}