package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeRequestDto {
    @NotBlank(message = "Personelin ismini boş bırakamazsınız !")
    String name;
    @NotBlank(message = "Personelin soyadını boş bırakamazsınız !")
    String surname;
    @NotNull(message = "Personel maaşını bırakamazsınız !")
    Double salary;
    String photo;
    @NotNull(message = "Personel'in doğum tarihini giriniz !")
    Long birthDate;
    String email;
    String companyName;
    String degree;
}
