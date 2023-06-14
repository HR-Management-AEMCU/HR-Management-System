package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.PersonnelPasswordModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonelPasswordProducer {
   /* @Value("${rabbitmq.user-exchange}")
    private String exchange;
    @Value("${rabbitmq.personnel-password-key}")
    private String personnelPasswordKey;

    //private final RabbitTemplate rabbitTemplate;

    public void sendPersonnelPassword(PersonnelPasswordModel model){
        System.out.println(model);
 //       rabbitTemplate.convertAndSend(exchange,personnelPasswordKey,model);
    }



    */
}
