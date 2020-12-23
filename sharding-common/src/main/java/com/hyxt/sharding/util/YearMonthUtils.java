package com.hyxt.sharding.util;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期年月数据的工具类
 * @description:
 * @author: lrh
 * @date: 2020/11/13 19:22
 */
public abstract class YearMonthUtils {

  static final DateTimeFormatter yyyyMMFormat = DateTimeFormat.forPattern("yyyyMM");


  public static List<String> getYearMonthNodes(String beginYearMonth,String endYearMonth) {
    DateTime begin = yyyyMMFormat.parseDateTime(beginYearMonth);
    DateTime end = yyyyMMFormat.parseDateTime(endYearMonth);

    List<String> result = Lists.newArrayList();

    result.add(beginYearMonth);
    for (; ; ) {
      if (end.isAfter(begin)) {
        begin = begin.plusMonths(1);
        result.add(yyyyMMFormat.print(begin));
      } else {
        break;
      }
    }
    return result;
  }

  public static Date parseYearMonthDate(String yyyyMM) {
    return yyyyMMFormat.parseDateTime(yyyyMM).toDate();
  }

  public static String formatYearMonthDate(Date date) {
    return yyyyMMFormat.print(new DateTime(date));
  }

}
