package com.bilgeadam.service;

import com.bilgeadam.dto.request.PersonnelAvansRequestDto;
import com.bilgeadam.dto.request.PersonnelSpendRequestDto;
import com.bilgeadam.dto.response.PersonnelAvansResponseDto;
import com.bilgeadam.dto.response.PersonnelSpendResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IAvansMapper;
import com.bilgeadam.mapper.ISpendMapper;
import com.bilgeadam.repository.ISpendRepository;
import com.bilgeadam.repository.entity.Avans;
import com.bilgeadam.repository.entity.Spend;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.EAvans;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatusAvans;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpendService extends ServiceManager<Spend,String> {

    private final ISpendRepository spendRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserProfileService userProfileService;

    public SpendService(ISpendRepository spendRepository, JwtTokenProvider jwtTokenProvider, UserProfileService userProfileService) {
        super(spendRepository);
        this.spendRepository = spendRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userProfileService = userProfileService;
    }
    public PersonnelSpendResponseDto savePersonnelSpend(PersonnelSpendRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUser = userProfileService.findByAuthId(authId.get());
        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println(optionalUser.get().getRoles());
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.PERSONNEL.toString())) {
            Spend spend= ISpendMapper.INSTANCE.fromPersonnelSpendRequestDtoToSpend(dto);
            spend.setName(optionalUser.get().getName());
            spend.setSurname(optionalUser.get().getSurname());
            spend.setPhone(optionalUser.get().getPhone());
            save(spend);
            PersonnelSpendResponseDto personnelSpendResponseDto = PersonnelSpendResponseDto.builder()
                    .name(optionalUser.get().getName())
                    .surname(optionalUser.get().getSurname())
                    .spendDate(spend.getSpendDate())
                    .spendDescription(spend.getSpendDescription())
                    .spendTotal(spend.getSpendTotal())
                    .spendPhoto(spend.getSpendPhoto())
                    .phone(optionalUser.get().getPhone())
                    .build();
            return personnelSpendResponseDto ;
        }else {
            throw new UserManagerException(ErrorType.ROLE_NOT_PERSONNEL);
        }
    }
    //veritabanında EAvans SPEND ve EstatusAvans PENDING olanları getiren findall metodu
    public List<Spend> findAvansAndStatusAvansPending() {
        List<Spend> spend = spendRepository.findByAvansAndStatusAvans(EAvans.SPEND, EStatusAvans.PENDING);
        if (spend.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println(spend);
        return spend;
    }
    public Boolean managerSpendStatusCheck(String token, String spendId) {
        Long authId = jwtTokenProvider.getIdFromToken(token).orElseThrow(() -> {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        });
        Optional<UserProfile> optionalManagerProfile = userProfileService.findByAuthId(authId);
        List<String> role = jwtTokenProvider.getRoleFromToken(token);
        if (role.contains(ERole.MANAGER.toString())) {
            if (optionalManagerProfile.isEmpty())
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            Optional<Spend> spend = findById(spendId);
            if (spend.isPresent()) {
                spend.get().setStatusAvans(EStatusAvans.ACTIVE);
                update(spend.get());
                return true;
            }
            throw new RuntimeException("NO MANAGER");
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }
    public Boolean managerSpendStatusCross(String token, String spendId) {
        Long authId = jwtTokenProvider.getIdFromToken(token).orElseThrow(() -> {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        });
        Optional<UserProfile> optionalManagerProfile = userProfileService.findByAuthId(authId);
        List<String> role = jwtTokenProvider.getRoleFromToken(token);
        if (role.contains(ERole.MANAGER.toString())) {
            if (optionalManagerProfile.isEmpty())
                throw new UserManagerException(ErrorType.USER_NOT_FOUND);
            Optional<Spend> spend = findById(spendId);
            if (spend.isPresent()) {
                spend.get().setStatusAvans(EStatusAvans.BANNED);
                update(spend.get());
                return true;
            }
            throw new RuntimeException("NO MANAGER");
        }
        throw new UserManagerException(ErrorType.AUTHORIZATION_ERROR);
    }





}
