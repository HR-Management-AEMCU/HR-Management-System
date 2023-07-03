package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Avans extends Base {
    @Id
    private String avansId;
    private String avansTotal;
    private String avansDescription;
    private Date avansDate;
    @Builder.Default
    private EStatusAvans statusAvans=EStatusAvans.PENDING;
    @Builder.Default
    private EAvans avans=EAvans.AVANS;
//    private String photo;
//    private String secondName;
//    private Long birthDate;
//    private Long authId;
//    private Long companyId;
    private String name;
    private String surname;
//    @Indexed(unique = true)
//    private String email;
//    private String password;
//    private Double salary; // TODO wage ile aynı anlamda düzelt
//    private EStatus status;
//    private String companyName;
//    private String taxNumber;
//    @Builder.Default
//    private Boolean isActivatedByAdmin = false;
//
//    private String birthPlace;
//    private String identificationNumber;
//    @Builder.Default
//    private List<ERole> roles = new ArrayList<>();
//    private EGender gender;
    private String phone;
//    private String neighbourhood;
//    private String district;
//    private String province;
//    private String country;
//    private Integer buildingNumber;
//    private Integer apartmentNumber;
//    private Integer postalCode;
//    private List<Long> employeeLeaves;
//    private String department;
//    private Long jobStartingDate;
//    private Long jobEndingDate;
//    private Date denemeTarihi;
    //avans talebi için ekstra propertys

}
