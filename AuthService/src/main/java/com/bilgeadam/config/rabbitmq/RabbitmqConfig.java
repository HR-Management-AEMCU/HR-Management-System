package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    private String exchange = "exchangeAuth";
    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }
    // mail activation code
    private String queueRegister = "queueRegister";
    private String bindingKeyRegister = "bindingKeyRegister";
    @Bean
    Queue registerQueue(){
        return new Queue(queueRegister);
    }
    @Bean
    public Binding bindingRegister(final Queue registerQueue, final DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(bindingKeyRegister);
    }
}
