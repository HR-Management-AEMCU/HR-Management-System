package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.ForgotPasswordMailResponseDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.ICompanyManager;
import com.bilgeadam.manager.IEmailManager;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.producer.MailRegisterProducer;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserProfileManager userManager;
    private final PasswordEncoder passwordEncoder;
    private final MailRegisterProducer mailRegisterProducer;
    private final IEmailManager iEmailManager;
    private final ICompanyManager iCompanyManager;


    public AuthService(IAuthRepository authRepository, JwtTokenProvider jwtTokenProvider,
                       IUserProfileManager userManager, PasswordEncoder passwordEncoder,
                       MailRegisterProducer mailRegisterProducer,
                       IEmailManager iEmailManager, ICompanyManager iCompanyManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
        this.mailRegisterProducer = mailRegisterProducer;

        this.iEmailManager = iEmailManager;
        this.iCompanyManager = iCompanyManager;
    }


    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.fromRegisterRequestDtoToAuth(dto);
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(auth.getEmail());

        if (optionalAuth.isEmpty()) {
            if (dto.getPassword().equals(dto.getRepassword())) {
                if (dto.getCompanyName() == null) {
                    auth.setRole(ERole.VISITOR);
                } else {
                    Set<GetCompanyResponseDto> getCompanies = iCompanyManager.getCompanies().getBody();
                    if (getCompanies.stream().anyMatch(company -> company.getCompanyName().equals(dto.getCompanyName())))
                    {
                        auth.setRole(ERole.DIRECTORY);
                    } else {
                        throw new AuthManagerException(ErrorType.COMPANY_NOT_FOUND);
                    }
                }
                auth.setActivationCode(CodeGenerator.generateCode());
                auth.setPassword(passwordEncoder.encode(dto.getPassword()));
                save(auth);
                userManager.createUserFromAuth(IAuthMapper.INSTANCE.fromAuthToCreateUserRequestDto(auth));
                // rabbitMQ
                mailRegisterProducer.sendActivationCode(IAuthMapper.INSTANCE.fromAuthToMailRegisterModel(auth));
            } else {
                throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
            }
            RegisterResponseDto responseDto = IAuthMapper.INSTANCE.fromAuthToRegisterResponseDto(auth);
            return responseDto;
        }
        throw new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
    }

    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth = findById(dto.getId());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        } else if (auth.get().getActivationCode().equals(dto.getActivationCode())) {
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activateStatus(auth.get().getAuthId());
            return true;
        }
        throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
    }

    public Boolean activateStatusLink(String token) {
        System.out.println(token);
        Optional<String> activationCode = jwtTokenProvider.getActivationCodeFromToken(token);
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<Auth> auth = findById(authId.get());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        } else if (auth.get().getActivationCode().equals(activationCode.get())) {
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activateStatus(auth.get().getAuthId());
            return true;
        }
        throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByEmail(dto.getEmail());
        System.out.println(auth.get().getAuthId());
        if (auth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), auth.get().getPassword())) {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        } else if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        Optional<String> token = jwtTokenProvider.createToken(auth.get().getAuthId(), auth.get().getRole());
        token.orElseThrow(() -> {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        });
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .token(token.get())
                .role(auth.get().getRole().toString())
                .build();

        return loginResponseDto;
    }

    public boolean createUser(RegisterRequestDto dto) {
        try {
            IAuthMapper iAuthMapper = IAuthMapper.INSTANCE;


            Auth auth = iAuthMapper.toUserAuth(dto);
            auth.setPassword(CodeGenerator.generateCode());
            authRepository.save(auth);


            iEmailManager.sendEmailAddressAndPassword(MailSenderDto.builder()
                    .content("Dear User\n\nYou can sign in using the information below. \n\nPassword:" + auth.getPassword() +
                            "\n \nEmail :" + auth.getEmail() + "\n \nHr Management")
                    .topic("Login Information")
                    .email(auth.getEmail())

                    .build());


            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    public Boolean passwordChange(FromUserProfilePasswordChangeDto dto) {
        Optional<Auth> auth = authRepository.findById(dto.getAuthId());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setPassword(dto.getPassword());
        authRepository.save(auth.get());
        return true;
    }

    public Boolean forgotPassword(String email) {
        Optional<Auth> auth = authRepository.findOptionalByEmail(email);
        if (auth.get().getStatus().equals(EStatus.ACTIVE)) {
            //random password variable
            String randomPassword = UUID.randomUUID().toString();
            auth.get().setPassword(passwordEncoder.encode(randomPassword));
            save(auth.get());
            ForgotPasswordMailResponseDto dto = ForgotPasswordMailResponseDto.builder()
                    .password(randomPassword)
                    .email(email)
                    .build();
            iEmailManager.forgotPasswordMail(dto);
            UserProfileChangePasswordRequestDto userProfileDto = UserProfileChangePasswordRequestDto.builder()
                    .authId(auth.get().getAuthId())
                    .password(auth.get().getPassword())
                    .build();
            userManager.forgotPassword(userProfileDto);
            return true;
        } else if (auth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        } else {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean activateDirector(String token, Long directorId) {
        Optional<String> adminRole = jwtTokenProvider.getRoleFromToken(token);
        Optional<Auth> director = findById(directorId);
        if (adminRole.get().isEmpty())
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        if (director.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (director.get().getIsActive()== false) {
            if (director.get().getCompanyName().isEmpty()) {
                director.get().setStatus(EStatus.BANNED);
            }
            director.get().setIsActive(true);
            save(director.get());
            userManager.activateDirector(directorId);
        }
        return true;
    }

}