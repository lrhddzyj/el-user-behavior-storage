package com.hyxt.domain.behavior.repository.query;

import java.util.Date;
import lombok.Data;

/**
 * 行为足迹的查询条件
 *
 * @description:
 * @author: lrh
 * @date: 2020/11/23 11:11
 */
@Data
public class BehaviorQueryVo {

  private String shopId;

  private Integer appcode;

  private Date beginDate;

  private Date endDate;

  private String storeId;

  private Integer dataSource;

  private String hyId;

  private String empId;

  private Integer type;

}
