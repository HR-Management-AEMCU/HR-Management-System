package com.bilgeadam.dto.response;

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
    String surname;
    Double salary;
    String email;
    String companyName;
    String degree;
    String photo;
    Long birthDate;
}
