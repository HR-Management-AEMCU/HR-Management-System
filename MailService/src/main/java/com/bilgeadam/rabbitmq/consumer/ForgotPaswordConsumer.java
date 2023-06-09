package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.ForgotPasswordMailModel;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPaswordConsumer {
    private final MailService mailService;

    @RabbitListener(queues = ("${rabbitmq.queueForgotPassword}"))
    public void sendMailForgotPasswordRequest(ForgotPasswordMailModel model){
        mailService.forgotPasswordRequestMail(model);
    }
}
