package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailRegisterConsumer {
    private final MailService mailService;
//    @RabbitListener(queues = "queueRegister")
//    public void sendMailRegister(MailRegisterModel model){
//        System.out.println(model.getEmail());
//        mailService.sendRegisterMail(model);
//    }
}
