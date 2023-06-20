package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.ManagerActivateStatusModel;
import com.bilgeadam.rabbitmq.model.PersonnelPasswordModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerActivateStatusConsumer {
    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.managerActivateQueue}")
    public void sendPersonnelPassword(ManagerActivateStatusModel model){
        System.out.println("true");
        mailService.managerActivationCodeMail(model);
    }
}
