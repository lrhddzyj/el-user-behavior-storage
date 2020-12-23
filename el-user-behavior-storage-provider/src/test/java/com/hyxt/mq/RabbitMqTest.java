package com.hyxt.mq;

import com.alibaba.fastjson.JSONObject;
import com.hyxt.configuration.RabbitMqConfiguration;
import com.hyxt.dto.FansGoodsBehaviorMessageDTO;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/26 10:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void send() {
    FansGoodsBehaviorMessageDTO fansGoodsBehaviorMessageDTO = new FansGoodsBehaviorMessageDTO();
    fansGoodsBehaviorMessageDTO.setId("111");
    fansGoodsBehaviorMessageDTO.setShopId("11");
    fansGoodsBehaviorMessageDTO.setStoreId("11");
    fansGoodsBehaviorMessageDTO.setEmpId("11");
    fansGoodsBehaviorMessageDTO.setHyId("11");
    fansGoodsBehaviorMessageDTO.setOpenId("11");
    fansGoodsBehaviorMessageDTO.setWxUnionId("11");
    fansGoodsBehaviorMessageDTO.setNickName("11");
    fansGoodsBehaviorMessageDTO.setPhoto("1");
    fansGoodsBehaviorMessageDTO.setGoodsId("1");
    fansGoodsBehaviorMessageDTO.setGoodsName("1");
    fansGoodsBehaviorMessageDTO.setGoodsUrl("1");
    fansGoodsBehaviorMessageDTO.setGoodsPicUrl("1");
    fansGoodsBehaviorMessageDTO.setViewStatus(0);
    fansGoodsBehaviorMessageDTO.setIsBuy(false);
    fansGoodsBehaviorMessageDTO.setIsKeep(false);
    fansGoodsBehaviorMessageDTO.setLiveTime(0);
    fansGoodsBehaviorMessageDTO.setViewDate("32");
    fansGoodsBehaviorMessageDTO.setViewTime("32");
    fansGoodsBehaviorMessageDTO.setViewCount(0);
    fansGoodsBehaviorMessageDTO.setLiveTimeAvg(0);
    fansGoodsBehaviorMessageDTO.setCreateTime("");
    fansGoodsBehaviorMessageDTO.setDataSource(0);
    fansGoodsBehaviorMessageDTO.setType(0);
    fansGoodsBehaviorMessageDTO.setRedirectUrl("11");
    rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_O2O,
        RabbitMqConfiguration.QUEUE_FANS_GOODS_BEHAVIOR, fansGoodsBehaviorMessageDTO);
  }

  @Test
  public void sendJSONStr() {
    JSONObject message = new JSONObject();
    message.put("id", UUID.randomUUID().toString());
    message.put("shopId", "1111");
    message.put("storeId", "");
    message.put("empId", "");
    message.put("hyId", "");
    message.put("goodsId", "");
    message.put("goodsName", "");
    message.put("goodsPicUrl", "");
    message.put("nickName", "");
    message.put("openId", "");
    message.put("wxUnionId", "");
    message.put("photo", "");
    message.put("type", 5);
    message.put("redirectUrl", "/pages/content/detail/detail?nid=");
    message.put("dataSource", 2);//数据来源：1=公众号（默认）,2=小程序
    message.put("appSource", 1);

    rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_O2O,
        RabbitMqConfiguration.QUEUE_FANS_GOODS_BEHAVIOR, message);
  }


}
