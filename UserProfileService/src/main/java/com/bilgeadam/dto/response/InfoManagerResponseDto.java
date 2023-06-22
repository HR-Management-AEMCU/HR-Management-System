package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoManagerResponseDto {
    private String name;
    private String surname;
    private String photo;
    private String email;
    private String companyName;
    private String taxNumber;
    private String identificationNumber;
    private String birthPlace;
    private String phone;
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
