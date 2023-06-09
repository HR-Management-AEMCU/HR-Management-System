package com.bilgeadam.manager;


import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UserProfileChangePasswordRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.FORGOT_PASSWORD;

@FeignClient(url = "http://localhost:8080/api/v1/user-profile", name = "auth-userprofile")
public  interface IUserProfileManager {
    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);
    @PostMapping("/create-user-from-auth")
    public ResponseEntity<Boolean> createUserFromAuth(@RequestBody CreateUserRequestDto dto);
    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserProfileChangePasswordRequestDto dto);

    @PostMapping("/activate-director/{directorId}")
    public ResponseEntity<Boolean> activateDirector(@PathVariable Long directorId);

}
