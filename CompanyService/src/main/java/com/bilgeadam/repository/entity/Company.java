package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblcompany")
//@EqualsAndHashCode(callSuper = false)
public class Company extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    @Column(unique = true)
    private String companyName;
    @Column(unique = true)
    private String taxNumber;
    private String companyLogoUrl;

    private String companyCountry;
    private String companyProvince;
    private String companyDistrict;
    private Integer companyBuildingNumber;
    private Integer companyApartmentNumber;
    private Integer companyPostalCode;

    private Double companyBalanceStatus;
    @ElementCollection
    @JoinTable(name = "comment", joinColumns = @JoinColumn(name = "companyId"))
    private List<Long> comments;
}
