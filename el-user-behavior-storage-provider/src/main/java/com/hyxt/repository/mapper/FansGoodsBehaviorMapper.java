package com.hyxt.repository.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hyxt.domain.behavior.entity.FansGoodsBehavior;
import com.hyxt.domain.behavior.repository.query.BehaviorQueryVo;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * FansGoodsBehavior mapper
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/23 11:28
 */
public interface FansGoodsBehaviorMapper extends BaseMapper<FansGoodsBehavior> {

  @Insert({
      "insert into fans_goods_behavior (id, shop_id, ",
      "store_id, emp_id, ",
      "hy_id, open_id, nick_name, ",
      "photo, goods_id, ",
      "goods_url, goods_name, ",
      "goods_pic_url, view_status, ",
      "is_buy, is_keep, live_time, ",
      "view_date, view_time, ",
      "create_time, data_source, ",
      "type, wx_union_id, ",
      "activity_id, redirect_url, ",
      "appcode, uuid)",
      "values (#{id,jdbcType=VARCHAR}, #{shopId,jdbcType=VARCHAR}, ",
      "#{storeId,jdbcType=VARCHAR}, #{empId,jdbcType=VARCHAR}, ",
      "#{hyId,jdbcType=VARCHAR}, #{openId,jdbcType=VARCHAR}, #{nickName,jdbcType=VARCHAR}, ",
      "#{photo,jdbcType=VARCHAR}, #{goodsId,jdbcType=VARCHAR}, ",
      "#{goodsUrl,jdbcType=VARCHAR}, #{goodsName,jdbcType=VARCHAR}, ",
      "#{goodsPicUrl,jdbcType=VARCHAR}, #{viewStatus,jdbcType=TINYINT}, ",
      "#{isBuy,jdbcType=TINYINT}, #{isKeep,jdbcType=TINYINT}, #{liveTime,jdbcType=INTEGER}, ",
      "#{viewDate,jdbcType=VARCHAR}, #{viewTime,jdbcType=VARCHAR}, ",
      "#{createTime,jdbcType=TIMESTAMP}, #{dataSource,jdbcType=INTEGER}, ",
      "#{type,jdbcType=INTEGER}, #{wxUnionId,jdbcType=VARCHAR}, ",
      "#{activityId,jdbcType=VARCHAR}, #{redirectUrl,jdbcType=VARCHAR}, ",
      "#{appcode,jdbcType=INTEGER}, #{uuid,jdbcType=VARCHAR})"
  })
  int save(FansGoodsBehavior record);

  List<FansGoodsBehavior> findPage(Pagination pagination,
      @Param("behaviorQueryVo") BehaviorQueryVo behaviorQueryVo);

  List<FansGoodsBehavior> findByUUID(@Param("uuid") String uuid,@Param("appcode") Integer appcode);

  void updateViewStatusAndTime(
      @Param("id") String id,@Param("appcode") Integer appcode, @Param("viewStatus") Integer viewStatus,
      @Param("liveTime") Integer liveTime);

  List<String> findTables(@Param("databaseName") String databaseName,
      @Param("tablePrefix") String tablePrefix);

  void createTable(@Param("tableName") String tableName);

}
