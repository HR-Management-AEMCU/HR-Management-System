package com.bilgeadam.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailRegisterModel implements Serializable {
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String activationCode;
}
