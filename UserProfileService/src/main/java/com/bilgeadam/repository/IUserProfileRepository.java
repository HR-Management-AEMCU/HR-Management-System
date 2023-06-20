package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByAuthId(Long authId);

    Optional<UserProfile> findByAuthIdAndRoles(Long authId,String role);
    List<UserProfile> findAllByCompanyName(String companyName);

    Optional<UserProfile> findByEmail(String email);

    List<UserProfile> findAllByRolesAndCompanyName(ERole employee, String companyName);

    List<UserProfile> findByRolesAndStatus(ERole role, EStatus status);



}
