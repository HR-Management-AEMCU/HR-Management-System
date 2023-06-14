package com.bilgeadam.manager;


import com.bilgeadam.dto.request.NewCreateAdminUserRequestDto;
import com.bilgeadam.dto.request.NewCreateManagerUserRequestDto;
import com.bilgeadam.dto.request.NewCreateVisitorUserRequestDto;
import com.bilgeadam.dto.request.UserProfileChangePasswordRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.FORGOT_PASSWORD;

@FeignClient(url = "http://localhost:8060/api/v1/user-profile", name = "auth-userprofile")
public  interface IUserProfileManager {
    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);

    @PostMapping("/create-visitor")
    public ResponseEntity<Boolean> createVisitorUser(@RequestBody NewCreateVisitorUserRequestDto dto);
    @PostMapping("/create-admin")
    public ResponseEntity<Boolean> createAdminUser(@RequestBody NewCreateAdminUserRequestDto dto);

    @PostMapping("/create-manager")
    public ResponseEntity<Boolean> createManagerUser(@RequestBody NewCreateManagerUserRequestDto dto);


    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserProfileChangePasswordRequestDto dto);

    @PostMapping("/activate-director/{directorId}")
    public ResponseEntity<Boolean> activateDirector(@PathVariable Long directorId);


    /*@PostMapping("/create-user-from-auth")
    public ResponseEntity<Boolean> createUserFromAuth(@RequestBody CreateUserRequestDto dto);
    * */
}
