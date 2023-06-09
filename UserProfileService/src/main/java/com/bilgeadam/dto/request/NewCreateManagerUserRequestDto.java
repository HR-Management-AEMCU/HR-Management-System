package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCreateManagerUserRequestDto {
    private Long authId;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String companyName;
    private String taxNumber;
}
