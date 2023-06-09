package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateEmployeeRequestDto;
import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.response.CreateEmployeeResponseDto;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.service.UserProfileService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequestMapping(USER_PROFILE)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Hidden
    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @Hidden
    @PostMapping("/create-user-from-auth")
    public ResponseEntity<Boolean> createUserFromAuth(@RequestBody CreateUserRequestDto dto) {
        return ResponseEntity.ok(userProfileService.createUserFromAuth(dto));
    }

    @PostMapping("/save-employee/{token}")
    public ResponseEntity<CreateEmployeeResponseDto> saveEmployee(@RequestBody @Valid CreateEmployeeRequestDto dto, @PathVariable String token){
        return ResponseEntity.ok(userProfileService.saveEmployee(dto,token));
    }
    @DeleteMapping("/delete-employee/{employeeId}/{token}")
    public ResponseEntity<Boolean> removeEmployee(@PathVariable String employeeId,@PathVariable String token){
        return ResponseEntity.ok(userProfileService.deleteEmployee(employeeId,token));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/find-by-company-name/{companyName}")
    public ResponseEntity<List<String>> findByCompanyName(@PathVariable String companyName){
        return ResponseEntity.ok(userProfileService.findByCompanyName(companyName));
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getEmployeeList(@RequestHeader("Authorization") String token) {
        List<String> employeeList = userProfileService.getEmployeeList(token);
        return ResponseEntity.ok(employeeList);
    }
    @Hidden
    @PostMapping("/activate-director/{directorId}")
    public ResponseEntity<Boolean> activateDirector(@PathVariable Long directorId){
        return ResponseEntity.ok(userProfileService.activateDirector(directorId));
    }

}
