package com.hyxt.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 * @description:
 * @author: lrh
 * @date: 2020/11/25 16:56
 */
@Configuration
public class RabbitMqConfiguration {

  public static final String EXCHANGE_O2O = "o2o";

  public static final String QUEUE_FANS_GOODS_BEHAVIOR = "o2o.behavior.fans_goods_el";

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean(EXCHANGE_O2O)
  public Exchange exchangeWitho2o() {
    return ExchangeBuilder.topicExchange(EXCHANGE_O2O).durable(true).build();
  }

  @Bean(QUEUE_FANS_GOODS_BEHAVIOR)
  public Queue queueWithfansGoodsBehavior() {
    return new Queue(QUEUE_FANS_GOODS_BEHAVIOR);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(queueWithfansGoodsBehavior()).to(exchangeWitho2o())
        .with(QUEUE_FANS_GOODS_BEHAVIOR).noargs();
  }

}
