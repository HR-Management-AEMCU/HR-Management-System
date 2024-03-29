package com.bilgeadam.manager;

import com.bilgeadam.dto.response.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "http://localhost:8060/api/v1/user-profile", name = "company-userprofile")
public interface IUserProfileManager {
    @GetMapping("/find-by-company-name/{companyName}")
    public ResponseEntity<List<String>> findByCompanyName(@PathVariable String companyName);

    @GetMapping("/find-by-userprofile-dto/{authId}")
    public ResponseEntity<UserProfileResponseDto> findByUserProfileDto(@PathVariable Long authId);

    @GetMapping("/list-for-salary/{token}")
    public ResponseEntity<List<Double>> getEmployeeListforSalary(@PathVariable String token);
}

