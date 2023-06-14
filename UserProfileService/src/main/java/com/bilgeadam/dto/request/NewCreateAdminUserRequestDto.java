package com.bilgeadam.dto.request;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCreateAdminUserRequestDto {
    private Long authId;
    private String password;
    private String email;
    private String name;
    private String surname;
    private EStatus status;
}
