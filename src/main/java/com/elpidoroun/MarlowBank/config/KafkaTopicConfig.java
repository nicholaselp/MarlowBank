package com.elpidoroun.MarlowBank.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String LOW_BALANCE_ALERT_TOPIC = "low-balance-alert";

    @Bean
    public NewTopic importRequestLineCreate(){
        return TopicBuilder.name(LOW_BALANCE_ALERT_TOPIC).build();
    }
}
