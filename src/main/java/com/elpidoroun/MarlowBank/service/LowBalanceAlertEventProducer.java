package com.elpidoroun.MarlowBank.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.elpidoroun.MarlowBank.config.KafkaTopicConfig.LOW_BALANCE_ALERT_TOPIC;

@AllArgsConstructor
@Service
public class LowBalanceAlertEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(LowBalanceAlertEventProducer.class);

    @NonNull
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String message){
        logger.info("Sending BalanceAlertEvent with message {}", message);
        kafkaTemplate.send(LOW_BALANCE_ALERT_TOPIC, message);
    }
}
