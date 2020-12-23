package com.hyxt.listener;

import static com.hyxt.configuration.RabbitMqConfiguration.QUEUE_FANS_GOODS_BEHAVIOR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyxt.dto.FansGoodsBehaviorMessageDTO;
import com.hyxt.service.FansGoodsBehaviorHandlerService;
import java.io.IOException;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**`
 * 用户行为的监听
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/25 17:00
 */
@Component
@Slf4j
public class UserBehaviorListener {

  private  ObjectMapper objectMapper = new ObjectMapper();

  private FansGoodsBehaviorHandlerService fansGoodsBehaviorHandlerService;

  public UserBehaviorListener(FansGoodsBehaviorHandlerService fansGoodsBehaviorHandlerService) {
    this.fansGoodsBehaviorHandlerService = fansGoodsBehaviorHandlerService;
  }

  @RabbitListener(queues = QUEUE_FANS_GOODS_BEHAVIOR)
  public void processMessage(Message message) {
    byte[] body = message.getBody();
    String messageStr = StringUtils.toEncodedString(body, Charset.defaultCharset());
    if (log.isDebugEnabled()) {
      log.debug("receive message from {}:\n{}", QUEUE_FANS_GOODS_BEHAVIOR,messageStr);
    }
    FansGoodsBehaviorMessageDTO fansGoodsBehaviorMessageDTO = null;
    try {
       fansGoodsBehaviorMessageDTO = objectMapper
          .readValue(messageStr, FansGoodsBehaviorMessageDTO.class);
    } catch (IOException e) {
      log.warn("接收到异常数据");
      return;
    }

    if (fansGoodsBehaviorMessageDTO.getAppSource() == null) {
      fansGoodsBehaviorMessageDTO.setAppSource(1);
    }

    if (fansGoodsBehaviorMessageDTO != null) {
      fansGoodsBehaviorHandlerService.handler(fansGoodsBehaviorMessageDTO);
    }
  }




}
