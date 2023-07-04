package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonnelSpendRequestDto {
    private String token;
    private Double spendTotal;
    private String spendDescription;
    private Date spendDate;
    private String spendPhoto;
























}
