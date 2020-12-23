package com.hyxt.sharding.component;


import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/24 16:45
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class SnowFlakeIdComponentTest {

  @Test
  public void testParseDate() {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd HHmmss");
    SnowFlakeIdComponent snowFlakeIdComponent = new SnowFlakeIdComponent(1, 1);
    long id = snowFlakeIdComponent.nextId();
    long longTime = snowFlakeIdComponent.parseDate(id);
    log.info("{}", formatter.print(new DateTime(longTime)));
  }


}