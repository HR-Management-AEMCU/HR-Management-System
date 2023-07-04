package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4100, "Kullancı adı veya şifre hatalı", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4200, "Şifreler aynı değil", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4300, "Bu kullanıcı zaten kayıtlı", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    AVANS_NOT_FOUND(4400, "Böyle bir avans istegi bulunamadı", HttpStatus.NOT_FOUND),
    ACTIVATE_CODE_ERROR(4500, "Aktivasyon kod hatası", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4600,"Token hatası" ,  HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4700, "Token oluşturulamadı", HttpStatus.BAD_REQUEST),
    ROLE_ERROR(4800,"Bu işlemi sadece manager rolüne sahip kişiler gerçekleştirebilir",HttpStatus.BAD_REQUEST),
    EMPLOYEE_NOT_FOUND(4900,"Böyle bir çalışan bulunmamaktadır.",HttpStatus.BAD_REQUEST),
    DIRECTORY_ERROR(5000,"Bu işlemi sadece o çalışanın firma yöneticisi gerçekleştirebilir",HttpStatus.BAD_REQUEST),
    AUTHORIZATION_ERROR(4200,"You're not authorized to do this.", HttpStatus.BAD_REQUEST),
    USER_NOT_MANAGER(4201,"This user is not Manager", HttpStatus.BAD_REQUEST),
    ROLE_NOT_VISITOR(4202,"Role Visitor Değil", HttpStatus.BAD_REQUEST),
    ROLE_NOT_PERSONNEL(4203,"Role Personnel Değil", HttpStatus.BAD_REQUEST),
    UPDATE_ROL_ERROR(5200,"VisitorUpdate işlemini sadece visitor gerçekleştirebilir",HttpStatus.BAD_REQUEST);
    private int code;
    private String message;
    HttpStatus httpStatus;
}
