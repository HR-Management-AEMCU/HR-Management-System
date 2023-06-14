package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterManagerRequestDto {

    @NotNull(message = "CompanyID boş bırakmayınız.")
    private Long companyId;
    @NotBlank(message = "Adınızı boş bırakmayınız.")
    private String name;
    @NotBlank(message = "Soyadınızı boş bırakmayınız.")
    private String surname;
    @Email(message = "Lütfen geçerli bir email giriniz.")
    private String email;
    @NotBlank
    @Size(min = 8, max = 32, message = "Şifre en az 8 en çok 32 karakter olabilir.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$",
            message = "Şifre en az bir büyük, bir küçük, harf, rakam, ve özel karakterden oluşmalıdır.")
    private String password;
    private String repassword;
    private String companyName;


}
