package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.EGender;
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
    private Long salaryDate;
    String photo;

    String email;
    String companyName;
    @NotNull(message = "Personel'in doğum tarihini giriniz !")
    Long birthDate;
    private String birthPlace;
    private String identificationNumber;
    private EGender gender;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private Integer buildingNumber;
    private Integer apartmentNumber;
    private Integer postalCode;
    private String department;
    private Long jobStartingDate;

























}
