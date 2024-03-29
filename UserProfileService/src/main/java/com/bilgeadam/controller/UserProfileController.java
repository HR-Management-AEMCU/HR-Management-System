package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;

import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.service.UserProfileService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/create-visitor")
    @Hidden
    public ResponseEntity<Boolean> createVisitorUser(@RequestBody NewCreateVisitorUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createVisitorUser(dto));
    }
    @PostMapping("/create-admin")
    @Hidden
    public ResponseEntity<Boolean> createAdminUser(@RequestBody NewCreateAdminUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createAdminUser(dto));
    }

    @PostMapping("/create-manager")
    @Hidden
    public ResponseEntity<Boolean> createManagerUser(@RequestBody NewCreateManagerUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createManagerUser(dto));
    }

    @GetMapping("/public-holidays")
    public ResponseEntity<List<String[]>> publicHolidays(){
        return ResponseEntity.ok(userProfileService.getPublicHolidays());
    }


//    @Hidden
//    @PostMapping("/create-user-from-auth")
//    public ResponseEntity<Boolean> createUserFromAuth(@RequestBody CreateUserRequestDto dto) {
//        return ResponseEntity.ok(userProfileService.createUserFromAuth(dto));
//    }

    @PostMapping("/save-employee")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<CreateEmployeeResponseDto> saveEmployee(@RequestBody @Valid CreateEmployeeRequestDto dto){
        return ResponseEntity.ok(userProfileService.saveEmployee(dto));
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

    @GetMapping("/personnel-list/{token}")
    public ResponseEntity<List<UserProfile>> getEmployeeList(@PathVariable String token) {
        return ResponseEntity.ok(userProfileService.getEmployeeList(token));
    }
    @GetMapping("/list-for-salary/{token}")
    @Hidden
    public ResponseEntity<List<Double>> getEmployeeListforSalary(@PathVariable String token) {
        return ResponseEntity.ok(userProfileService.getEmployeeListforSalary(token));
    }

    /*@Hidden
    @PostMapping("/activate-director/{directorId}")
    public ResponseEntity<Boolean> activateDirector(@PathVariable Long directorId){
        return ResponseEntity.ok(userProfileService.activateDirector(directorId));
    }
*/
    @PutMapping(ADMIN_CHANGE_MANAGER_STATUS_CHECK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> adminChangeManagerStatusCheck(String token,String userId) {
        return ResponseEntity.ok(userProfileService.adminChangeManagerStatusCheck(token,userId));
    }
    @PutMapping(ADMIN_CHANGE_MANAGER_STATUS_CROSS)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> adminChangeManagerStatusCross(String token,String userId) {
        return ResponseEntity.ok(userProfileService.adminChangeManagerStatusCross(token,userId));
    }

    @GetMapping("/role-manager-status-inactive")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<UserProfile>> findRoleManagerAndStatusInactive(){
        return ResponseEntity.ok(userProfileService.findRoleManagerAndStatusInactive());
    }

    @GetMapping("/find-by-userprofile-dto/{authId}")
    @Hidden
    public ResponseEntity<Optional<UserProfile>> findByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.findByAuthId(authId));
    }
    @PostMapping("/manager-change-role")
    public ResponseEntity<Boolean> managerChangeRole(String token, String userId){
        return ResponseEntity.ok(userProfileService.managerChangeRole(token, userId));
    }
    //updateVisitor metodu için
    @PostMapping(UPDATE_VISITOR)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> updateVisitor(@RequestBody UpdateVisitorRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateVisitor(dto));
    }
    //updatePersonnel metodu için
    @PostMapping(UPDATE_PERSONNEL)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> updatePersonnel(@RequestBody UpdatePersonnelRequestDto dto){
        return ResponseEntity.ok(userProfileService.updatePersonnel(dto));
    }
    //infoProfileVisitor
    @PostMapping(INFO_VISITOR)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<InfoVisitorResponseDto> infoProfileVisitor(@RequestBody InfoVisitorRequestDto dto){
        return ResponseEntity.ok(userProfileService.infoProfileVisitor(dto));
    }
    @PostMapping(ADMIN_MANAGER_APPROVAL+ "/{token}")
    public ResponseEntity<Boolean> adminManagerApproval(@PathVariable String token, String userId, Boolean action){
        return ResponseEntity.ok(userProfileService.adminManagerApproval(token, userId, action));
    }

    @PostMapping(INFO_PERSONEL)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<InfoPersonelResponseDto> infoProfilePersonel(@RequestBody InfoPersonelRequestDto dto){
        return ResponseEntity.ok(userProfileService.infoProfilePersonel(dto));
    }
    @PostMapping(INFO_MANAGER)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<InfoManagerResponseDto> infoProfileManager(@RequestBody InfoManagerRequestDto dto){
        return ResponseEntity.ok(userProfileService.infoProfileManager(dto));
    }

    @GetMapping(SEARCH_PERSONNEL)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Page<UserProfile>> searchPageCompany(@RequestParam String text, @RequestParam int page){
        return ResponseEntity.ok(userProfileService.userProfileListSearch(text,page));
    }










}
