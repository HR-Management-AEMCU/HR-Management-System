package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequestDto {
    private String token;
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
    //gecice olarak ekrana paraları yazdırabilmek için eklendi
    private Double income;
    private Double outcome;
}
