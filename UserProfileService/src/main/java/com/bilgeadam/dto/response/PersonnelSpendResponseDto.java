package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonnelSpendResponseDto {

    private String name;
    private String surname;
    private String phone;
    private Double spendTotal;
    private String spendDescription;
    private Date spendDate;
    private String spendPhoto;

























}
