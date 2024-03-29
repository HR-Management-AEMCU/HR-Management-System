package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoPersonelResponseDto {
    String name;
    String surname;
    String photo;
    String email;
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
    private Double salary;
    private String companyName;
    private String taxNumber;
    private String phone;
    private String department;
    private Long jobStartingDate;
    private Long jobEndingDate;
}
