package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoCompanyResponseDto {
    private String companyName;
    private String taxNumber;
    private String companyLogoUrl;
    private String companyCountry;
    private String companyProvince;
    private String companyDistrict;
    private Integer companyBuildingNumber;
    private Integer companyApartmentNumber;
    private Integer companyPostalCode;
    private Double companyBalanceStatus;
    private Double income;
    private Double outcome;

}
