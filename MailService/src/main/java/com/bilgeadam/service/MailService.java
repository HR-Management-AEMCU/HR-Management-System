package com.bilgeadam.service;


import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.utility.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final JwtTokenProvider jwtTokenProvider;

    public void sendRegisterMail (MailRegisterModel model){
        System.out.println(model.getEmail());
        Optional<String> token = jwtTokenProvider.createToken(model);
        System.out.println(token.get());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Aktivasyon Linki");
        mailMessage.setText("Sayın, "+ model.getName() +" "+model.getSurname()+ " üyelik aktivasyon linkiniz: http://localhost:8090/api/v1/auth/activate-status-with-link/"+token.get());
        javaMailSender.send(mailMessage);
    }
   /* public String sendMail(MailSenderDto dto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);


        try {
            messageHelper.setTo(dto.getEmail());
            messageHelper.setText(dto.getContent());
            messageHelper.setSubject(dto.getTopic());

        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        javaMailSender.send(mimeMessage);
        return "Mail Sent!";
    }*/
}


