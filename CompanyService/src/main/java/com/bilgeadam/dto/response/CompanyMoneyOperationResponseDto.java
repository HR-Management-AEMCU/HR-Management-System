package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyMoneyOperationResponseDto {
    private Double income;
    private Double outcome;
    private Double profitLoss;
    private Double payments;
}
