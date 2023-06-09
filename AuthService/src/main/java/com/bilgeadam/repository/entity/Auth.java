package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

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
    private String taxNumber;


    @ElementCollection(targetClass = ERole.class)
    @JoinTable(name = "tblRoleTypes", joinColumns = @JoinColumn(name = "authId"))
    @Column(name = "roleType", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<ERole> roles;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status=EStatus.PENDING;
    @Builder.Default
    private Boolean isActive=false;
}
