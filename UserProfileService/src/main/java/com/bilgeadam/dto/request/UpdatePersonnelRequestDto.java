package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonnelRequestDto {
    private String token;
    private String photo;
    private Long birthDate;
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
    private Double salary;
    private String companyName;
    private String phone;
    private String department;
    private Long jobStartingDate;
}
