package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailRegisterProducer {
    private String exchange = "exchangeAuth";
    private String bindingKeyRegister = "bindingKeyRegister";

    private final RabbitTemplate rabbitTemplate;
    public void sendActivationCode(MailRegisterModel model){
        rabbitTemplate.convertAndSend(exchange,bindingKeyRegister,model);
    }
}
