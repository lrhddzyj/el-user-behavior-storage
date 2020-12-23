package com.hyxt.service;

import com.hyxt.dto.FansGoodsBehaviorMessageDTO;

/**
 * 用户行为的消息的接收服务
 * @description:
 * @author: lrh
 * @date: 2020/11/26 11:39
 */
public interface FansGoodsBehaviorHandlerService {

  /**
   * 消息处理
   * @param fansGoodsBehaviorDTO
   */
  void handler(FansGoodsBehaviorMessageDTO fansGoodsBehaviorDTO);

}
