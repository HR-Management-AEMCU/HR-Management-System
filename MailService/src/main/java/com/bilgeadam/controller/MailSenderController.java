package com.bilgeadam.controller;

import com.bilgeadam.dto.MailSenderDto;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.MAIL;


@RestController
@RequiredArgsConstructor
@RequestMapping(MAIL)
public class MailSenderController {
    private final MailService mailService;

   /* @CrossOrigin("*")
    @PostMapping("/sendmail")
    public void sendMailAddressAndPassword(@RequestBody MailSenderDto dto) {
        mailService.sendMail(dto);
    }*/
}
