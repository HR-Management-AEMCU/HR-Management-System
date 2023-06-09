package com.bilgeadam.service;


import com.bilgeadam.dto.request.CreateEmployeeRequestDto;
import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.response.CreateEmployeeResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.EmailPasswordGenerator;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.PasswordGenerator;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenProvider jwtTokenProvider) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Boolean createUserFromAuth(CreateUserRequestDto dto) {
        save(IUserProfileMapper.INSTANCE.createUserRequestDtoToUserProfile(dto));
        return true;
    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByAuthId(authId);
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    public CreateEmployeeResponseDto saveEmployee(CreateEmployeeRequestDto dto, String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalUser.get().getRole().toString().equals(ERole.DIRECTORY.toString())) {
            UserProfile user = IUserProfileMapper.INSTANCE.createEmployeeRequestDtoToUserProfile(dto);
            if (dto.getEmail() == null) {
                String generatedEmail = dto.getName().toLowerCase() + "." +
                        dto.getSurname().toLowerCase() + "@" + dto.getCompanyName().toLowerCase() + ".com";
                user.setEmail(generatedEmail);
            }
            String generatedPassword = EmailPasswordGenerator.generatePassword();
            user.setPassword(generatedPassword);
            user.setRole(ERole.EMPLOYEE);
            user.setStatus(EStatus.ACTIVE);
            save(user);
            return IUserProfileMapper.INSTANCE.userProfileToCreateEmployeeResponseDto(user);
        }
        throw new UserManagerException(ErrorType.ROLE_ERROR);
    }

    public Boolean deleteEmployee(String employeeId, String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<UserProfile> userProfile = userProfileRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println(userProfile);
        if (userProfile.get().getRole().equals(ERole.DIRECTORY)) {
            Optional<UserProfile> employee = findById(employeeId);
            if (userProfile.get().getCompanyName().toLowerCase().equals(employee.get().getCompanyName().toLowerCase())) {
                if (employee.isEmpty()) {
                    throw new UserManagerException(ErrorType.EMPLOYEE_NOT_FOUND);
                }
                employee.get().setStatus(EStatus.DELETED);
                update(employee.get());
                return true;
            } else {
                throw new UserManagerException(ErrorType.DIRECTORY_ERROR);
            }
        }
        throw new UserManagerException(ErrorType.ROLE_ERROR);
    }

    public List<String> findByCompanyName(String companyName) {
        List<UserProfile> userProfileList = userProfileRepository.findAllByCompanyName(companyName);
        /*if (userProfileList.size()==0) {
        // todo if controlü çalışmıyor kontrol edilmesi ve refactor edilmesi lazım

            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }*/
        List<String> results = new ArrayList<>();
        for(UserProfile user: userProfileList){
            // todo bu kontrol admin onay metodundan sonra eklenecek
            // user.getIsActivatedByAdmin().equals(true) &&
            if (user.getRole().equals(ERole.DIRECTORY)) {
                String fullName = user.getName()+" "+user.getSurname();
                results.add(fullName);
            }
        }
        return results;
    }

    public List<String> getEmployeeList(String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalUser.get().getRole().equals(ERole.DIRECTORY)) {
            List<UserProfile> employeeList = userProfileRepository.findAllByRoleAndCompanyName(ERole.EMPLOYEE, optionalUser.get().getCompanyName());
            List<String> employeeNames = new ArrayList<>();
            for (UserProfile employee : employeeList) {
                String fullName = employee.getName() + " " + employee.getSurname();
                employeeNames.add(fullName);
            }
            return employeeNames;
        }
        throw new UserManagerException(ErrorType.ROLE_ERROR);

    }
    public Boolean activateDirector(Long directorId){
        Optional<UserProfile> optionalUser= userProfileRepository.findByAuthId(directorId);
        if (optionalUser.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        optionalUser.get().setIsActivatedByAdmin(true);
        update(optionalUser.get());
        return true;

    }
}
