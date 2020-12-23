package com.hyxt.sharding.algorithm;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.hyxt.sharding.constant.DynamicActualDataConstant;
import com.hyxt.sharding.util.YearMonthUtils;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

/**
 * 按年月的时间区间的分表算法
 * @description:
 * @author: lrh
 * @date: 2020/11/16 17:12
 */
@Slf4j
public class YearMonthDateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

  /**
   * 最小时间，在这个之前的时间的数据全部进入默认表
   */
  private  Date lowerDate;

  /**
   * 默认表
   */
  private  String defaultTableName;

  public YearMonthDateRangeShardingAlgorithm() {

  }

  public YearMonthDateRangeShardingAlgorithm(Date lowerDate, String defaultTableName) {
    this.lowerDate = lowerDate;
    this.defaultTableName = defaultTableName;
  }

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      RangeShardingValue<Date> shardingValue) {
    Collection<String> searchTables = Sets.newConcurrentHashSet();
    String logicTableName = shardingValue.getLogicTableName();
    Range<Date> dates = shardingValue.getValueRange();
    Date beginPoint = dates.lowerEndpoint();
    Date endpoint = dates.upperEndpoint();

    String beginYearMonth = YearMonthUtils.formatYearMonthDate(beginPoint);
    String endYearMonth = YearMonthUtils.formatYearMonthDate(endpoint);

    List<String> yearMonthNodes = YearMonthUtils.getYearMonthNodes(beginYearMonth, endYearMonth);
    for (String yearMonthNode : yearMonthNodes) {
      String searcheTable = buildSearcheTable(logicTableName, yearMonthNode);
      searchTables.add(searcheTable);
    }
    if (log.isDebugEnabled()) {
      log.debug("要查询的表集合:{}", searchTables);
    }
    return searchTables;
  }

  /**
   * 构建查询表
   * @param logicTableName
   * @param addingYearMonth
   * @return
   */
  private String buildSearcheTable(String logicTableName,String addingYearMonth) {
    boolean hasDefault = lowerDate != null && StringUtils.isNotBlank(defaultTableName);
    if (hasDefault) {
      Date yearMonthDate = YearMonthUtils.parseYearMonthDate(addingYearMonth);
      if (yearMonthDate.before(lowerDate)) {
        return defaultTableName;
      }
    }
    String tableName = logicTableName + DynamicActualDataConstant.SEPERATOR + addingYearMonth;
    return tableName;
  }


}
