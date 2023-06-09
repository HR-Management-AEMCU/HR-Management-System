package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "auths")
public class Auth extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String activationCode;
    private String password;
    private String companyName;
    private String degree;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ERole role=ERole.VISITOR;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status=EStatus.PENDING;
    @Builder.Default
    private Boolean isActive=false;
}
