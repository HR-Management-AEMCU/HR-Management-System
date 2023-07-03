package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.EAvans;
import com.bilgeadam.repository.enums.EGender;
import com.bilgeadam.repository.enums.EStatusAvans;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonnelAvansRequestDto {
    private String token;
    private String avansTotal;
    private String avansDescription;
    private Date avansDate;
























}
