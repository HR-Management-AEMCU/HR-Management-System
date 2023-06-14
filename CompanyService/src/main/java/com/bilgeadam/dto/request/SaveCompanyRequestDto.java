package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCompanyRequestDto {
    @NotBlank(message = "Lütfen şirket adını giriniz.")
    private String companyName;
    @NotBlank(message = "Lütfen şirket vergi numarasını giriniz.")
    private String taxNumber;
    private String companyDistrict;
    private String companyProvince;
    private String companyCountry;
    private Integer companyBuildingNumber;
    private Integer companyApartmentNumber;
    private Integer companyPostalCode;
    private Double companyBalanceStatus;
}
