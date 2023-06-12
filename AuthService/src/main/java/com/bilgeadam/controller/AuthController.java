package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.dto.response.UpdateManagerStatusResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.repository.entity.Auth;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;


    @PostMapping(REGISTER_VISITOR)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> registerVisitor(@RequestBody @Valid RegisterVisitorRequestDto dto){
        return ResponseEntity.ok(authService.registerVisitor(dto));
    }
    @PostMapping(REGISTER_ADMIN)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> registerAdmin(@RequestBody @Valid RegisterVisitorRequestDto dto){
        return ResponseEntity.ok(authService.registerAdmin(dto));
    }
    @PostMapping(REGISTER_MANAGER)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<RegisterResponseDto> registerManager(@RequestBody @Valid RegisterManagerRequestDto dto){
        return ResponseEntity.ok(authService.registerManager(dto));
    }
    @GetMapping(FIND_ALL)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }
    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @GetMapping(ACTIVATE_STATUS+"-with-link/{token}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable String token){
        return ResponseEntity.ok(authService.activateStatusLink(token));
    }
    @CrossOrigin
    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
    @CrossOrigin
    @PostMapping(LOGIN_ADMIN)
    public ResponseEntity<LoginResponseDto> loginAdmin(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

//    @PostMapping("/createuser")
//    public ResponseEntity<Boolean> createUser(@RequestBody @Valid RegisterVisitorRequestDto dto) {
//        try {
//            authService.createUser(dto);
//            return ResponseEntity.ok(true);
//        } catch (Exception e) {
//            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);//usernotcreated
//        }
//    }

    @PutMapping(PASSWORD_CHANGE)
    public ResponseEntity<Boolean> passwordChange(@RequestBody FromUserProfilePasswordChangeDto dto){
        return ResponseEntity.ok(authService.passwordChange(dto));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(FORGOT_PASSWORD_REQUEST + "/{email}")
    public ResponseEntity<Boolean> forgotPasswordRequest(@PathVariable String email){
        return ResponseEntity.ok(authService.forgotPasswordRequest(email));
    }
    /*@PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(String email){
        return ResponseEntity.ok(authService.forgotPassword(email));
    }*/

    /*@PostMapping("/activate-director/{token}/{directorId}")
    public ResponseEntity<Boolean> activateDirector(@PathVariable String token, @PathVariable Long directorId){
        return ResponseEntity.ok(authService.activateDirector(token, directorId));
    }*/

    @PutMapping("/update-manager-status")
    @Hidden
    public ResponseEntity<Boolean> updateManagerStatus(@RequestBody UpdateManagerStatusResponseDto dto){
        return ResponseEntity.ok(authService.updateManagerStatus(dto));
    }



}
