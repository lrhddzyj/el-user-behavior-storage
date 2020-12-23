package com.hyxt.domain.behavior.sharding;

import static com.hyxt.domain.behavior.BehaviorConstant.BEHAVIOR_LOGIC_TABLE_NAME;
import static com.hyxt.sharding.constant.ShardingDataSourceConstant.DEFAULT_SHARDING_DATA_SOURCE_NAME;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.hyxt.sharding.component.SnowFlakeIdComponent;
import com.hyxt.sharding.rule.TableRuleBuildService;
import com.hyxt.sharding.util.YearMonthUtils;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 用户行为的分片规则的创建服务
 * @description:
 * @author: lrh
 * @date: 2020/11/19 21:17
 */
@Service
@Slf4j
public class BehaviorTableRuleBuildService implements TableRuleBuildService {

  private SnowFlakeIdComponent snowFlakeIdComponent;

  public BehaviorTableRuleBuildService(
      SnowFlakeIdComponent snowFlakeIdComponent) {
    this.snowFlakeIdComponent = snowFlakeIdComponent;
  }

  private static final String MULTI_TABLE_SHARDING_COLUMN = "id,create_time,uuid";

  private static final String DEFAULT_ACTUAL_DATA_NODES =
      "${['" + DEFAULT_SHARDING_DATA_SOURCE_NAME + "." + BEHAVIOR_LOGIC_TABLE_NAME + "']}";

  @Override
  public TableRuleConfiguration build() {
    TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration(
        BEHAVIOR_LOGIC_TABLE_NAME, DEFAULT_ACTUAL_DATA_NODES);
//    orderTableRuleConfig
//        .setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_id", getProperties()));
    ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration = buildStandardShardingStrategyConfiguration();
    orderTableRuleConfig.setTableShardingStrategyConfig(complexShardingStrategyConfiguration);
    orderTableRuleConfig.setDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
    return orderTableRuleConfig;
  }

  /**
   * 构建复合分片算法的配置
   * @return
   */
  private ComplexShardingStrategyConfiguration buildStandardShardingStrategyConfiguration() {
    return new ComplexShardingStrategyConfiguration(MULTI_TABLE_SHARDING_COLUMN,
        new ComplexKeysShardingAlgorithm() {
          @Override
          public Collection<String> doSharding(Collection availableTargetNames,
              ComplexKeysShardingValue shardingValue) {
            Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
            String logicTableName = shardingValue.getLogicTableName();

            boolean hasId = columnNameAndShardingValuesMap.containsKey("id");
            if (hasId) {
              String preciseTable = preciseMatchTableById(logicTableName, availableTargetNames, shardingValue);
              if (log.isDebugEnabled()) {
                log.debug("根据ID精确匹配table:{}", preciseTable);
              }
              return Lists.newArrayList(preciseTable);
            }
            boolean hasUUID = columnNameAndShardingValuesMap.containsKey("uuid");
            if (hasUUID) {
              Collection<String> result = rangeMatchTablesWithUUID(logicTableName, availableTargetNames, shardingValue);
              if (log.isDebugEnabled()) {
                log.debug("根据UUID匹配table:{}",result);
              }
              return Lists.newArrayList(result);
            }

            Collection<String> result = rangeMatchTables(logicTableName, availableTargetNames,
                shardingValue);
            if (log.isDebugEnabled()) {
              log.debug("选择查询数据的tables:{}", result);
            }
            return result;
          }

          /**
           * 精确匹配数据表
           * @param logicTableName
           * @param availableTargetNames
           * @param shardingValue
           * @return
           */
          private String preciseMatchTableById(String logicTableName,Collection<String> availableTargetNames,
              ComplexKeysShardingValue shardingValue) {
            Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();

            Date createTime = null;
            //从id中反向解析
            List<String> idList = (List<String>) columnNameAndShardingValuesMap.get("id");
            if (!CollectionUtils.isEmpty(idList)) {
              String id = idList.get(0);
              long longTime = snowFlakeIdComponent.parseDate(Long.parseLong(id));
              createTime = new DateTime(longTime).toDate();
            } else {
              List<Date> createTimeList = (List<Date>) columnNameAndShardingValuesMap.get("create_time");
              if (CollectionUtils.isEmpty(createTimeList)) {
                return logicTableName;
              }
              createTime = createTimeList.get(0);
            }

            String  yyyyMM = YearMonthUtils.formatYearMonthDate(createTime);
            String firstLevelMatchTableName = logicTableName + "_" + yyyyMM;
            List<String> firstMatchList = Lists.newArrayList();
            for (String availableTargetName : availableTargetNames) {
              boolean exist = StringUtils.indexOf(availableTargetName, firstLevelMatchTableName) > -1;
              if (exist) {
                firstMatchList.add(availableTargetName);
              }
            }

            if (CollectionUtils.isEmpty(firstMatchList)) {
              return logicTableName;
            }

            Integer appcode = null;
            List<Integer> appcodeList = (List<Integer>) columnNameAndShardingValuesMap.get("appcode");
            if (!CollectionUtils.isEmpty(appcodeList)) {
              appcode = appcodeList.get(0);
            }
            if (appcode != null) {
              String secondMatchTableName = firstLevelMatchTableName + "_" + appcode;
              //优先有appcode的表
              for (String tableName : firstMatchList) {
                if (StringUtils.equals(tableName, secondMatchTableName)) {
                  return tableName;
                }
              }
            }

            //找到按日期匹配的表
            for (String tableName : firstMatchList) {
              if (StringUtils.equals(tableName, firstLevelMatchTableName)) {
                return tableName;
              }
            }

            //如果还找不到则用逻辑表
            return logicTableName;
          }

          /**
           * 范围匹配数据表
           * @param logicTableName
           * @param availableTargetNames
           * @param shardingValue
           * @return
           */
          private Collection<String> rangeMatchTables(String logicTableName,Collection<String> availableTargetNames,
              ComplexKeysShardingValue shardingValue) {
            Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
            Map columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();

           /* List<Integer> appcodeList = (List<Integer>) columnNameAndShardingValuesMap.get("appcode");
            if (CollectionUtils.isEmpty(appcodeList)) {
              return Lists.newArrayList(logicTableName);
            }
            Integer appcode = appcodeList.get(0);*/

            Range<Date> createTimeRange = (Range<Date>)columnNameAndRangeValuesMap.get("create_time");
            Date lowerDate = createTimeRange.lowerEndpoint();
            Date upperDate = createTimeRange.upperEndpoint();
            List<String> yearMonthNodes = YearMonthUtils
                .getYearMonthNodes(YearMonthUtils.formatYearMonthDate(lowerDate),
                    YearMonthUtils.formatYearMonthDate(upperDate));
            Set<String> searchTableNames = Sets.newHashSet();
            for (String yearMonthNode : yearMonthNodes) {
              String tableNameWithNoAppcode = logicTableName + "_" + yearMonthNode;
              searchTableNames.add(tableNameWithNoAppcode);
            /*  String tableNameWithAppcode = tableNameWithNoAppcode + "_" + appcode;
              searchTableNames.add(tableNameWithAppcode);*/
            }
            HashSet<String> availableTargetSet = Sets.newHashSet(availableTargetNames);
            SetView<String> intersection = Sets.intersection(availableTargetSet, searchTableNames);
            if (!CollectionUtils.isEmpty(intersection)) {
              return Lists.newArrayList(intersection);
            }

            return Lists.newArrayList(logicTableName);
          }

          /**
           * 用UUID模糊匹配数据表
           * @param logicTableName
           * @param availableTargetNames
           * @param shardingValue
           * @return
           */
          private Collection<String> rangeMatchTablesWithUUID(String logicTableName,Collection<String> availableTargetNames,
              ComplexKeysShardingValue shardingValue) {

            Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();

            Integer appcode = null;
            List<Integer> appcodeList = (List<Integer>) columnNameAndShardingValuesMap.get("appcode");
            if (!CollectionUtils.isEmpty(appcodeList)) {
               appcode = appcodeList.get(0);
            }

            DateTime now = DateTime.now();

            List<String> yearMonthNodes = Lists.newArrayList();
            yearMonthNodes.add(YearMonthUtils.formatYearMonthDate(now.toDate()));
            //避免出现数据在跨月的时间点上找不到的情况
            boolean needChoosePreTable = startDayOfMonthAndHeadHalfHour(now);
            if (needChoosePreTable) {
              DateTime preDayTime = now.plusDays(1);
              yearMonthNodes.add(YearMonthUtils.formatYearMonthDate(preDayTime.toDate()));
            }

            Set<String> searchTableNames = Sets.newHashSet();
            for (String yearMonthNode : yearMonthNodes) {
              String tableNameWithNoAppcode = logicTableName + "_" + yearMonthNode;
              searchTableNames.add(tableNameWithNoAppcode);
              if (appcode != null) {
                String tableNameWithAppcode = tableNameWithNoAppcode + "_" + appcode;
                searchTableNames.add(tableNameWithAppcode);
              }
            }
            HashSet<String> availableTargetSet = Sets.newHashSet(availableTargetNames);
            SetView<String> intersection = Sets.intersection(availableTargetSet, searchTableNames);
            if (!CollectionUtils.isEmpty(intersection)) {
              return Lists.newArrayList(intersection);
            }

            return Lists.newArrayList(logicTableName);
          }

          /**
           * 检测时间是否是当月的第一天的半个小时内
           * @return
           */
          public boolean startDayOfMonthAndHeadHalfHour(DateTime dateTime) {
            DateTime now = DateTime.now();
            DateTime monthStartTime = now.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
            DateTime monthStartTimeInOneHour = monthStartTime.plusMinutes(30);
            return dateTime.isAfter(monthStartTime) && dateTime.isBefore(monthStartTimeInOneHour);
          }
        });
  }

  }

