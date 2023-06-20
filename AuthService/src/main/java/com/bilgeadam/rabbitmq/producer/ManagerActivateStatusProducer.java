package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.ForgotPasswordMailModel;
import com.bilgeadam.rabbitmq.model.ManagerActivateStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerActivateStatusProducer {
    @Value("${rabbitmq.exchange-auth}")
    private String exchange;
    @Value("${rabbitmq.managerActivateQueueBindingKey}")
    private String managerActivateQueueBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendActivationCode(ManagerActivateStatusModel model){
        rabbitTemplate.convertAndSend(exchange,managerActivateQueueBindingKey,model);
    }
}
