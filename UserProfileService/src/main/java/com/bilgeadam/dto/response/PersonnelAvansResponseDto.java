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
public class PersonnelAvansResponseDto {

    private String name;
    private String surname;
    private String phone;
    private String avansTotal;
    private String avansDescription;
    private Date avansDate;

























}
