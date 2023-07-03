package com.bilgeadam.service;


import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.manager.ICompanyManager;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.rabbitmq.model.PersonnelPasswordModel;
import com.bilgeadam.rabbitmq.producer.PersonelPasswordProducer;
import com.bilgeadam.repository.IUserProfileRepository;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Normalizer;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ICompanyManager companyManager;
    private final IAuthManager authManager;

    private final PasswordEncoder passwordEncoder;
    private final PersonelPasswordProducer personelPasswordProducer;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenProvider jwtTokenProvider, ICompanyManager companyManager, IAuthManager authManager, PasswordEncoder passwordEncoder, PersonelPasswordProducer personelPasswordProducer) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.companyManager = companyManager;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.personelPasswordProducer = personelPasswordProducer;
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
        userProfile.setRoles(roleList);
        save(userProfile);
        return true;
    }

    public Boolean createAdminUser(NewCreateAdminUserRequestDto dto) {
        UserProfile userProfile = IUserProfileMapper.INSTANCE.fromNewCreateAdminUserResponseDtoToUserProfile(dto);
        List<ERole> roleList = new ArrayList<>();
        roleList.add(ERole.ADMIN);
        userProfile.setRoles(roleList);
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
        userProfile.setRoles(roleList);
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
        userProfile.get().setStatus(EStatus.INACTIVE);
        update(userProfile.get());
        return true;
    }

    //email formatının harf duyarlılığı için
    private String normalize(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalizedString.replaceAll("[^\\p{ASCII}]", "");
    }

    public CreateEmployeeResponseDto saveEmployee(CreateEmployeeRequestDto dto) {
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        Optional<Long> managerId = jwtTokenProvider.getIdFromToken(dto.getToken());
        if (role.contains(ERole.MANAGER.toString())) {
            Optional<UserProfile> optionalUserProfile = userProfileRepository.findByEmail(dto.getEmail());
            if (optionalUserProfile.isEmpty()) {
                UserProfile user = IUserProfileMapper.INSTANCE.createEmployeeRequestDtoToUserProfile(dto);
            /*if (dto.getEmail() == null || dto.getEmail() == "") {
                String generatedEmail = normalize(dto.getName().toLowerCase()) + "." +
                        normalize(dto.getSurname().toLowerCase()) + "@" + normalize(dto.getCompanyName().toLowerCase()).replaceAll(" ", "") + ".com";
                user.setEmail(generatedEmail);
            }*/
                Optional<UserProfile> managerProfile = userProfileRepository.findByAuthId(managerId.get());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                List<ERole> roleList = new ArrayList<>();
                roleList.add(ERole.PERSONNEL);
                user.setRoles(roleList);
                user.setStatus(EStatus.ACTIVE);
                user.setCompanyId(managerProfile.get().getCompanyId());
                AuthCreatePersonnelProfileRequestDto authDto =
                        IUserProfileMapper.INSTANCE.fromUserProfileToAuthCreatePersonnelProfileRequestDto(user);
                System.out.println(authDto);
                Long personnelAuthId = authManager.managerCreatePersonnelUserProfile(authDto).getBody();
                user.setAuthId(personnelAuthId);
                save(user);
            PersonnelPasswordModel personnelPasswordModel = IUserProfileMapper.INSTANCE.fromUserProfileToPersonnelPasswordModel(user);
            personnelPasswordModel.setPassword(dto.getPassword());
            personelPasswordProducer.sendPersonnelPassword(personnelPasswordModel);


                return IUserProfileMapper.INSTANCE.userProfileToCreateEmployeeResponseDto(user);
            }
            throw new UserManagerException(ErrorType.USERNAME_DUPLICATE);
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }


    public Boolean deleteEmployee(String employeeId, String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<UserProfile> userProfile = userProfileRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println(userProfile);
        if (userProfile.get().getRoles().toString().contains(ERole.MANAGER.toString())) {
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
        for (UserProfile user : userProfileList) {
            // todo bu kontrol admin onay metodundan sonra eklenecek
            // user.getIsActivatedByAdmin().equals(true) &&
            if (user.getRoles().equals(ERole.MANAGER)) {
                String fullName = user.getName() + " " + user.getSurname();
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
        String companyName = user.get().getCompanyName();
        System.out.println(companyName);
        if (user.get().getRoles().toString().contains(ERole.MANAGER.toString())) {
            List<UserProfile> employeeList = userProfileRepository.findAllByCompanyName(companyName);
            return employeeList;
        }
        throw new UserManagerException(ErrorType.ROLE_ERROR);
    }
    public List<Double> getEmployeeListforSalary(String token) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        Optional<UserProfile> user = userProfileRepository.findByAuthId(authId.get());
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        String companyName = user.get().getCompanyName();
        System.out.println(companyName);
        System.out.println(user);
        if (user.get().getRoles().toString().contains(ERole.MANAGER.toString())) {
            List<UserProfile> employeeList = userProfileRepository.findAllByCompanyName(companyName);
            List<Double> salaries = new ArrayList<>();
            for( UserProfile employee : employeeList){
                salaries.add(employee.getSalary());
            }
            return salaries;
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
    public Boolean adminChangeManagerStatusCheck(String token, String userId) {
        Long authId = jwtTokenProvider.getIdFromToken(token).orElseThrow(() -> {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        });
        Optional<UserProfile> optionalAdminProfile = userProfileRepository.findByAuthId(authId);
        List<String> role = jwtTokenProvider.getRoleFromToken(token);
        if (role.contains(ERole.ADMIN.toString())) {
            if (optionalAdminProfile.isEmpty())
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            Optional<UserProfile> user = findById(userId);
            if (user.get().getRoles().contains(ERole.MANAGER)) {
                user.get().setStatus(EStatus.ACTIVE);
                update(user.get());
                authManager.updateManagerStatus(IUserProfileMapper.INSTANCE.fromUserProfileToUpdateManagerStatusRequestDto(user.get()));
                return true;
            }
            throw new RuntimeException("NO MANAGER");
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }

    public Boolean adminChangeManagerStatusCross(String token, String userId) {
        Long authId = jwtTokenProvider.getIdFromToken(token).orElseThrow(() -> {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        });
        Optional<UserProfile> optionalAdminProfile = userProfileRepository.findByAuthId(authId);
        List<String> role = jwtTokenProvider.getRoleFromToken(token);
        if (role.contains(ERole.ADMIN.toString())) {
            if (optionalAdminProfile.isEmpty())
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            Optional<UserProfile> user = findById(userId);
            if (user.get().getRoles().contains(ERole.MANAGER)) {
                user.get().setStatus(EStatus.BANNED);
                update(user.get());
                authManager.updateManagerStatus(IUserProfileMapper.INSTANCE.fromUserProfileToUpdateManagerStatusRequestDto(user.get()));
                return true;
            }
            throw new RuntimeException("NO MANAGER");
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }


    private static final String API_BASE_URL = "https://date.nager.at/api/v3/PublicHolidays";

    private List<String[]> parsePublicHolidays(String responseBody) {
        List<String[]> publicHolidays = new ArrayList<>();
        try {
            JSONArray holidaysArray = new JSONArray(responseBody);
            for (int i = 0; i < holidaysArray.length(); i++) {
                JSONObject holidayObject = holidaysArray.getJSONObject(i);
                String date = holidayObject.getString("date");
                String name = holidayObject.getString("name");
                String[] holidayData = {date, name};
                publicHolidays.add(holidayData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return publicHolidays;
    }

    public List<String[]> getPublicHolidays() {
        int currentYear = Year.now().getValue();
        String endpointUrl = API_BASE_URL + "/" + currentYear + "/TR";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .header("Accept", "application/json")
                .build();
        try {
            CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = responseFuture.get();
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                List<String[]> publicHolidays = parsePublicHolidays(responseBody);
                return publicHolidays;
            } else {
                System.err.println("API request failed with status code: " + response.statusCode());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //For Comment
    public Optional<UserProfile> findByAuthId(Long authId) {
        Optional<UserProfile> userProfile = userProfileRepository.findByAuthId(authId);
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        return userProfile;
    }

    //veritabanında Rolu manager olan ve Status INACTIVE olanları getiren findall metodu
    public List<UserProfile> findRoleManagerAndStatusInactive() {
        List<UserProfile> userProfile = userProfileRepository.findByRolesAndStatus(ERole.MANAGER, EStatus.INACTIVE);
        System.out.println(userProfile);

        return userProfile;
    }

    //upsadateVisitor metodu--> register olurken giremediği bilgileri update metoduyla girebiilir
    public Boolean updateVisitor(UpdateVisitorRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.VISITOR.toString())) {
            UserProfile user = IUserProfileMapper.INSTANCE.fromUpdateVisitorRequestDtoToUserProfile(dto, optionalUser.get());
            System.out.println(user);
            update(user);
            return true;
        }
        throw new UserManagerException(ErrorType.UPDATE_ROL_ERROR);
    }
    //upsadatePersonnel metodu--> bu metotta name surname password değiştirme gidi işler eklencekse authserviceyede gitmeli bilgiler.
    public Boolean updatePersonnel(UpdatePersonnelRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.PERSONNEL.toString())) {
            UserProfile user = IUserProfileMapper.INSTANCE.fromUpdatePersonnelRequestDtoToUserProfile(dto, optionalUser.get());
            System.out.println(user);
            update(user);
            return true;
        }
        throw new UserManagerException(ErrorType.UPDATE_ROL_ERROR);
    }

    //task28 profil kartı oluşturmak için role=visitor olup tokendaki id ile bilgilerinin çekilmesi
    public InfoVisitorResponseDto infoProfileVisitor(InfoVisitorRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.VISITOR.toString())) {
            InfoVisitorResponseDto infoVisitor = IUserProfileMapper.INSTANCE.fromUserPRofileToInfoVisitorResponseDto(optionalUser.get());
            return infoVisitor;
        }else {
            throw new UserManagerException(ErrorType.ROLE_NOT_VISITOR);
        }
    }

    public InfoPersonelResponseDto infoProfilePersonel(InfoPersonelRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println(optionalUser.get().getRoles());
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.PERSONNEL.toString())) {
            InfoPersonelResponseDto infoPersonel = IUserProfileMapper.INSTANCE.fromUserPRofileToInfoPersonelResponseDto(optionalUser.get());
            return infoPersonel;
        }else {
            throw new UserManagerException(ErrorType.ROLE_NOT_PERSONNEL);
        }
    }

    public Boolean managerChangeRole(String token, String userId) {
        List<String> userRole = jwtTokenProvider.getRoleFromToken(token);
        if (userRole.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        UserProfile userProfile = findById(userId).orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND));
        if (userRole.contains(ERole.MANAGER.toString())) {
            List<ERole> personnelRoleList = new ArrayList<>();
            personnelRoleList.remove(ERole.MANAGER);
            userProfile.setRoles(personnelRoleList);
            userProfileRepository.save(userProfile);
            return true;
        }
        throw new UserManagerException(ErrorType.USER_NOT_MANAGER);
    }

    public Boolean adminManagerApproval(String token, String userId, Boolean action) {
        List<String> adminRole = jwtTokenProvider.getRoleFromToken(token);
        System.out.println(adminRole.stream().toList());
        if (adminRole.contains(ERole.ADMIN.toString())) {
            Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userId);
            if (optionalUserProfile.isPresent()) {
                UserProfile userProfile = optionalUserProfile.get();
                if (action) {
                    userProfile.setStatus(EStatus.ACTIVE);
                } else {
                    userProfile.setStatus(EStatus.INACTIVE);
                }
                userProfileRepository.save(userProfile);
                return true;
            }
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }

    public InfoManagerResponseDto infoProfileManager(InfoManagerRequestDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authId.get());
        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println(optionalUser.get().getRoles());
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.MANAGER.toString())) {
            InfoManagerResponseDto infoManager = IUserProfileMapper.INSTANCE.fromUserPRofileToInfoManagerResponseDto(optionalUser.get());
            return infoManager;
        }else {
            throw new UserManagerException(ErrorType.ROLE_NOT_PERSONNEL);
        }
    }
}