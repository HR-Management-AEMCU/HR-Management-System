package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.queueForgotPassword}")
    private String forgotPasswordQueue;

    @Bean
    Queue forgotMailQueue(){
        return new Queue(forgotPasswordQueue);
    }



//Personnel password sending
    @Value("${rabbitmq.queuePersonnelPassword}")
    private String personnelPasswordQueue;

    @Bean
    Queue personnelPasswordQueue(){return new Queue(personnelPasswordQueue);}

    @Value("${rabbitmq.managerActivateQueue}")
    private String managerActivateQueue;

    @Bean
    Queue managerActivateQueue(){return new Queue(managerActivateQueue);}



//    private String queueRegister = "queueRegister";
//    @Bean
//    Queue registerQueue(){
//        return new Queue(queueRegister);
//    }
}
