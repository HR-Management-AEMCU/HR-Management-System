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
public class CreateEmployeeResponseDto {
    String name;

    private Long companyId;
    String surname;
    Double salary;
    private Long salaryDate;
    String photo;
    String email;
    String companyName;
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
