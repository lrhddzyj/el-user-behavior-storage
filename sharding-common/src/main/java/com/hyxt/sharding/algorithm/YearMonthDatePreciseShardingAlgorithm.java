package com.hyxt.sharding.algorithm;

import com.hyxt.sharding.constant.DynamicActualDataConstant;
import com.hyxt.sharding.util.YearMonthUtils;
import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * 按年月的精确分片算法
 * @description:
 * @author: lrh
 * @date: 2020/11/16 16:54
 */
@Slf4j
public class YearMonthDatePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

  /**
   * 最小时间，在这个之前的时间的数据全部进入默认表
   */
  private  Date lowerDate;

  /**
   * 默认表
   */
  private  String defaultTableName;

  public YearMonthDatePreciseShardingAlgorithm() {
  }

  public YearMonthDatePreciseShardingAlgorithm(Date defaultLowerDate,String defaultTableName) {
    this.lowerDate = defaultLowerDate;
    this.defaultTableName = defaultTableName;
  }

  @Override
  public String doSharding(Collection<String> availableTargetNames,
      PreciseShardingValue<Date> shardingValue) {
    String loginTableName = shardingValue.getLogicTableName();
    Date createTime = shardingValue.getValue();
    return buildInsertTable(loginTableName, createTime);
  }

  private String buildInsertTable(String loginTableName, Date createTime) {
    boolean hasDefault = lowerDate != null && StringUtils.isNotBlank(defaultTableName);
    if (hasDefault) {
      if(createTime == null || createTime.before(lowerDate) ){
        log.info("时间分片参数为空，或者当前时间:{} 小于最低时间 {} ，进入默认表", createTime, YearMonthUtils.formatYearMonthDate(lowerDate));
        return defaultTableName;
      }
    }
    String  yyyyMM = YearMonthUtils.formatYearMonthDate(createTime);
    String tableName = loginTableName + DynamicActualDataConstant.SEPERATOR + yyyyMM;
    return tableName;
  }
}
