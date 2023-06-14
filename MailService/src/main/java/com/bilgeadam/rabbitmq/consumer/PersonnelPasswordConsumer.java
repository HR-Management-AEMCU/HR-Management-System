package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.PersonnelPasswordModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonnelPasswordConsumer {
    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.queuePersonnelPassword}")
    public void sendPersonnelPassword(PersonnelPasswordModel model){
        System.out.println("true");
        mailService.personnelPasswordMail(model);
    }
}
