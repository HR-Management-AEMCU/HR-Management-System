package com.bilgeadam.dto.response;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateManagerStatusResponseDto {
    private EStatus status;
    private Long authId;
}
