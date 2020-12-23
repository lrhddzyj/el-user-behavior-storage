package com.hyxt;

import java.nio.charset.Charset;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/23 10:52
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonTest {

  @Test
  public void rabdom() {
    for (int i = 0; i < 100; i++) {
      System.out.println(RandomUtils.nextInt(0, 2));
    }
  }

  @Test
  public void dayOfMonth() {
    DateTime now = DateTime.now();
    DateTime dateTime = now.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
    DateTime dateTime2 = now.dayOfMonth().withMinimumValue().withTimeAtStartOfDay().plusHours(1);
    System.out.println(dateTime.toString());
    System.out.println(dateTime2.toString());
  }

  @Test
  public void charset() {
    Charset charset = Charset.defaultCharset();
    System.out.println(charset);

  }



}
