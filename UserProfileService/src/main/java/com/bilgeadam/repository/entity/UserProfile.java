package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String email;
    private String password;
    private Double salary;
    private EStatus status;
    private ERole role;
    private String companyName;
    @Builder.Default
    private Boolean isActivatedByAdmin = false;
}
