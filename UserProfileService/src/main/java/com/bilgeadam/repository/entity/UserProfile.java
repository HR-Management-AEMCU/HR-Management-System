package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EGender;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class UserProfile extends Base {
    @Id
    private String userId;
    private String photo;
    private String secondName;
    private Long birthDate;
    private Long authId;
    private String name;
    private String surname;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Double salary; // TODO wage ile aynı anlamda düzelt
    private EStatus status;
    private String companyName;
    private String taxNumber;
    @Builder.Default
    private Boolean isActivatedByAdmin = false;

    private String birthPlace;
    private String identificationNumber;
    @Builder.Default
    private List<ERole> role = new ArrayList<>();
    private EGender gender;
    private String phone;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private Integer buildingNumber;
    private Integer apartmentNumber;
    private Integer postalCode;
    private String companyId;
    private List<Long> employeeLeaves;
    private String department;
    private Long jobStartingDate;
    private Long jobEndingDate;
}
