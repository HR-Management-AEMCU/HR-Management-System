package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.exchange-auth}")
    private String exchange;

    @Bean
    DirectExchange directExchange() {return new DirectExchange(exchange);}
    @Value("${rabbitmq.queueForgotPassword}")
    private String forgotPasswordQueue;
    @Value("${rabbitmq.forgotPasswordMailBindingKey}")
    private String forgotPasswordMailBindingKey;
    @Bean
    Queue forgotPasswordQueue(){return new Queue(forgotPasswordQueue);}
    @Bean
    public Binding bindingForgotPasswordQueue(final Queue forgotPasswordQueue,final DirectExchange exchange) {
        return BindingBuilder.bind(forgotPasswordQueue).to(exchange).with(forgotPasswordMailBindingKey);
    }
        @Value("${rabbitmq.managerActivateQueue}")
        private String managerActivateQueue;
    @Value("${rabbitmq.managerActivateQueueBindingKey}")
    private String managerActivateQueueBindingKey;

    @Bean
    Queue managerActivateQueue(){ return new Queue(managerActivateQueue);
    }

    @Bean
    public Binding bindingManagerActivateQueue(final Queue managerActivateQueue,final DirectExchange exchange) {
        return BindingBuilder.bind(managerActivateQueue).to(exchange).with(managerActivateQueueBindingKey);
    }


//    private String exchange = "exchangeAuth";
//    @Bean
//    DirectExchange exchangeAuth(){
//        return new DirectExchange(exchange);
//    }
//    // mail activation code
//    private String queueRegister = "queueRegister";
//    private String bindingKeyRegister = "bindingKeyRegister";
//
//    @Bean
//    Queue registerQueue(){
//        return new Queue(queueRegister);
//    }
//    @Bean
//    public Binding bindingRegister(final Queue registerQueue, final DirectExchange exchangeAuth){
//        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(bindingKeyRegister);
//    }
}
