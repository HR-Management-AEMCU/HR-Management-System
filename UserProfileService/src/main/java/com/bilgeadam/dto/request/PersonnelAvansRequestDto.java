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
public class PersonnelAvansRequestDto {
    private String token;
    private Double avansTotal;
    private String avansDescription;
    private Date avansDate;
























}
