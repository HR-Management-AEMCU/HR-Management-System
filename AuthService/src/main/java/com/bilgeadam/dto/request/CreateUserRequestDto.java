package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDto {
    Long authId;
    String name;
    String surname;
    String email;
    String password;
    ERole role;
    String companyName;
}
