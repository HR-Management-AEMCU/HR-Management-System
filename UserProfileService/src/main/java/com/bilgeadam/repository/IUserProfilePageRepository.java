package com.bilgeadam.repository;


import com.bilgeadam.repository.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserProfilePageRepository extends MongoRepository<UserProfile, String> {
    Page<UserProfile> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String name, String surname, Pageable pageable);

}