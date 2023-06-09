package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @Email(message = "Lütfen geçerli bir email giriniz !")
    String email;
    @NotBlank(message = "Lütfen isminizi giriniz")
    String name;
    @NotBlank(message = "Lütfen soyadınızı giriniz")
    String surname;
    @NotBlank
    @Size(min = 8, max = 32, message = "Şifre en az 8 en fazla 32 karekter olabilir. ")
    String password;
    String repassword;
    String degree;
    String companyName;


}
