package com.hyxt.service.impl;

import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import com.hyxt.domain.behavior.service.FansGoodsBehaviorService;
import com.hyxt.dto.FansGoodsBehaviorMessageDTO;
import com.hyxt.service.FansGoodsBehaviorHandlerService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

/**
 * 用户行为的消息的接收服务
 * @description:
 * @author: lrh
 * @date: 2020/11/26 11:40
 */
@Service
public class FansGoodsBehaviorHandlerServiceImpl implements FansGoodsBehaviorHandlerService {

  private FansGoodsBehaviorService fansGoodsBehaviorService;

  public FansGoodsBehaviorHandlerServiceImpl(
      FansGoodsBehaviorService fansGoodsBehaviorService) {
    this.fansGoodsBehaviorService = fansGoodsBehaviorService;
  }

  @Override
  public void handler(FansGoodsBehaviorMessageDTO fansGoodsBehaviorDTO) {
    String uuid = fansGoodsBehaviorDTO.getId();
    Integer appSource = fansGoodsBehaviorDTO.getAppSource();

    FansGoodsBehavior behavior = fansGoodsBehaviorService.findByUUID(uuid, appSource);
    if (behavior == null) {
      FansGoodsBehavior fansGoodsBehavior = build(fansGoodsBehaviorDTO);
      fansGoodsBehaviorService.save(fansGoodsBehavior);
    }else{
      if (fansGoodsBehaviorDTO.getViewStatus() == 1) {
        String id = behavior.getId();
        int appcode = behavior.getAppcode();
        Integer viewStatus = fansGoodsBehaviorDTO.getViewStatus();
        Integer liveTime = fansGoodsBehaviorDTO.getLiveTime();
        fansGoodsBehaviorService.updateViewStatusAndTime(id, appcode, viewStatus, liveTime);
      }
    }
  }

  static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
  static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm:ss");

  private FansGoodsBehavior build(FansGoodsBehaviorMessageDTO dto) {
    DateTime now = DateTime.now();
    FansGoodsBehavior behavior = new FansGoodsBehavior();
    behavior.setShopId(dto.getShopId());
    behavior.setStoreId(dto.getStoreId());
    behavior.setEmpId(dto.getEmpId());
    behavior.setHyId(dto.getHyId());
    behavior.setOpenId(dto.getOpenId());
    behavior.setWxUnionId(dto.getWxUnionId());
    behavior.setNickName(dto.getNickName());
    behavior.setPhoto(dto.getPhoto());
    behavior.setGoodsId(dto.getGoodsId());
    behavior.setGoodsName(dto.getGoodsName());
    behavior.setGoodsUrl(dto.getGoodsUrl());
    behavior.setGoodsPicUrl(dto.getGoodsPicUrl());
    behavior.setViewStatus(dto.getViewStatus());
    behavior.setIsBuy(dto.getIsBuy());
    behavior.setIsKeep(dto.getIsKeep());
    behavior.setLiveTime(1);
    behavior.setViewDate(dateFormatter.print(now));
    behavior.setViewTime(timeFormatter.print(now));
    behavior.setCreateTime(now.toDate());
    behavior.setDataSource(dto.getDataSource());
    behavior.setType(dto.getType());
    behavior.setRedirectUrl(dto.getRedirectUrl());
    behavior.setActivityId("");
    behavior.setAppcode(dto.getAppSource());
    behavior.setUuid(dto.getId());
    return behavior;
  }



}
