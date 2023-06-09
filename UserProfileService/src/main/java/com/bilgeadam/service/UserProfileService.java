package com.bilgeadam.service;


import com.bilgeadam.dto.request.CreateEmployeeRequestDto;
import com.bilgeadam.dto.request.NewCreateManagerUserRequestDto;
import com.bilgeadam.dto.request.NewCreateVisitorUserRequestDto;
import com.bilgeadam.dto.response.CreateEmployeeResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.manager.ICompanyManager;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.EmailPasswordGenerator;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ICompanyManager companyManager;
    private final IAuthManager authManager;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenProvider jwtTokenProvider, ICompanyManager companyManager, IAuthManager authManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.companyManager = companyManager;
        this.authManager = authManager;
    }

    /*public Boolean createUserFromAuth(CreateUserRequestDto dto) {
        save(IUserProfileMapper.INSTANCE.createUserRequestDtoToUserProfile(dto));
        return true;
    }*/

    //managerHidden
    public Boolean createVisitorUser(NewCreateVisitorUserRequestDto dto) {
        UserProfile userProfile = IUserProfileMapper.INSTANCE.fromNewCreateVisitorUserResponseDtoToUserProfile(dto);
        List<ERole> roleList = new ArrayList<>();
        roleList.add(ERole.VISITOR);
        userProfile.setRole(roleList);
        save(userProfile);
        return true;
    }
    //managerHidden
    public Boolean createManagerUser(NewCreateManagerUserRequestDto dto) {
        //String companyId = companyManager.saveCompanyRequestDto(IUserProfileMapper.INSTANCE.fromNewCreateManagerUserResponseDtoToSaveCompanyRequestDto(dto)).getBody();
        UserProfile userProfile = IUserProfileMapper.INSTANCE.fromNewCreateManagerUserResponseDtoToUserProfile(dto);
        List<ERole> roleList = new ArrayList<>();
        roleList.add(ERole.MANAGER);
        roleList.add(ERole.PERSONNEL);
        userProfile.setStatus(EStatus.PENDING);
        userProfile.setRole(roleList);
        //userProfile.setCompanyId(companyId);
        save(userProfile);
        return true;
    }
    //managerHidden
    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByAuthId(authId);
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    //email formatının harf duyarlılığı için
    private String normalize(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalizedString.replaceAll("[^\\p{ASCII}]", "");
    }

    public CreateEmployeeResponseDto saveEmployee(CreateEmployeeRequestDto dto, String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println("gelen role verisi"+optionalUser.get().getRole().toString());
        if (optionalUser.get().getRole().toString().contains(ERole.MANAGER.toString())) {
            UserProfile user = IUserProfileMapper.INSTANCE.createEmployeeRequestDtoToUserProfile(dto);
            if (dto.getEmail() == null || dto.getEmail()== "") {
                String generatedEmail = normalize(dto.getName().toLowerCase()) + "." +
                        normalize(dto.getSurname().toLowerCase()) + "@" + normalize(dto.getCompanyName().toLowerCase()).replaceAll(" ", "") + ".com";
                user.setEmail(generatedEmail);
            }
            String generatedPassword = EmailPasswordGenerator.generatePassword();
            user.setPassword(generatedPassword);
            List<ERole> roleList = new ArrayList<>();
            roleList.add(ERole.PERSONNEL);
            user.setRole(roleList);
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
        if (userProfile.get().getRole().toString().contains(ERole.MANAGER.toString())) {
            Optional<UserProfile> employee = findById(employeeId);
            System.out.println(employee);
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

    //ziyatretcinin kullancagı searc baar için
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
            if (user.getRole().equals(ERole.MANAGER)) {
                String fullName = user.getName()+" "+user.getSurname();
                results.add(fullName);
            }
        }
        return results;
    }

    public List<UserProfile> getEmployeeList(String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        System.out.println(authId);
        Optional<UserProfile> user = userProfileRepository.findByAuthId(authId.get());
        System.out.println(user);
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        String companyName=user.get().getCompanyName();
        System.out.println(companyName);
        if (user.get().getRole().toString().contains(ERole.MANAGER.toString())) {
            List<UserProfile> employeeList = userProfileRepository.findAllByCompanyName(companyName);
            return employeeList;
        }
        throw new UserManagerException(ErrorType.ROLE_ERROR);
    }
    /*public Boolean activateDirector(Long directorId){
        Optional<UserProfile> optionalUser= userProfileRepository.findByAuthId(directorId);
        if (optionalUser.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        optionalUser.get().setIsActivatedByAdmin(true);
        update(optionalUser.get());
        return true;

    }*/
    public Boolean adminChangeManagerStatus(String token, String userId, Boolean action) {
        Long authId = jwtTokenProvider.getIdFromToken(token).orElseThrow(() -> {throw new UserManagerException(ErrorType.INVALID_TOKEN);});
        Optional<UserProfile> optionalAdminProfile = userProfileRepository.findByAuthId(authId);
        List<String> role = jwtTokenProvider.getRoleFromToken(token);
        if(role.contains(ERole.ADMIN.toString())) {
            if (optionalAdminProfile.isEmpty())
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            Optional<UserProfile> user = findById(userId);
            if (user.get().getRole().contains(ERole.MANAGER)) {
                if (action) {
                    user.get().setStatus(EStatus.ACTIVE);
                } else {
                    user.get().setStatus(EStatus.BANNED);
                }
                update(user.get());
                authManager.updateManagerStatus(IUserProfileMapper.INSTANCE.fromUserProfileToUpdateManagerStatusRequestDto(user.get()));
                return true;
            }
            throw new RuntimeException("NO MANAGER");
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }
}
