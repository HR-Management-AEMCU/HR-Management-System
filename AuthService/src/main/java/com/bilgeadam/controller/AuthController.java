package com.bilgeadam.controller;

import com.bilgeadam.dto.request.FromUserProfilePasswordChangeDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.repository.entity.Auth;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.bilgeadam.dto.request.ActivateRequestDto;
import com.bilgeadam.dto.request.LoginRequestDto;
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
    @CrossOrigin
    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register (@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @GetMapping(FIND_ALL)
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

    @PostMapping("/createuser")
    public ResponseEntity<Boolean> createUser(@RequestBody @Valid RegisterRequestDto dto) {
        try {
            authService.createUser(dto);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);//usernotcreated
        }
    }
    @PutMapping(PASSWORD_CHANGE)
    public ResponseEntity<Boolean> passwordChange(@RequestBody FromUserProfilePasswordChangeDto dto){
        return ResponseEntity.ok(authService.passwordChange(dto));
    }
    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(String email){
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PostMapping("/activate-director/{token}/{directorId}")
    public ResponseEntity<Boolean> activateDirector(@PathVariable String token, @PathVariable Long directorId){
        return ResponseEntity.ok(authService.activateDirector(token, directorId));
    }



}
