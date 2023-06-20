package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCreatePersonnelProfileResponseDto {
    private String email;
    private String name;
    private String surname;
    private String password;
    private List<ERole> roles;
    private EStatus status;
}
