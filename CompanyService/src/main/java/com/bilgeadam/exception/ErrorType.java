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
    COMPANY_DUPLICATE(4302, "Bu şirket zaten kayıtlı", HttpStatus.BAD_REQUEST),// globalde çağırılmış düzenleme gerekli
    INCOME_DUPLICATE(4301, "Bu income zaten kayıtlı, var olan bir gelire yenisi eklenemez", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    COMPANY_NOT_FOUND(4401, "Böyle bir şirket bulunamadı",HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(4402, "Böyle bir yorum bulunamadı",HttpStatus.NOT_FOUND),
    TOKEN_NOT_FOUND(4404, "Böyle bir token bulunamadı", HttpStatus.NOT_FOUND),
    AUTHORIZATION_ERROR(4405, "you have no permission to continue", HttpStatus.BAD_REQUEST),
    COMPANY_ERROR(4900,"You can only comment to your company.", HttpStatus.BAD_REQUEST),
    COMMENT_STATUS_ERROR(4905,"Your comment was not Approved", HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4500, "Aktivasyon kod hatası", HttpStatus.BAD_REQUEST);



    private int code;
    private String message;
    HttpStatus httpStatus;
}
