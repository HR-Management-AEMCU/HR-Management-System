package com.bilgeadam.service;

import com.bilgeadam.dto.request.PersonnelAvansRequestDto;
import com.bilgeadam.dto.response.PersonnelAvansResponseDto;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IAvansMapper;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.repository.IAvansRepository;
import com.bilgeadam.repository.entity.Avans;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvansService extends ServiceManager<Avans,String> {

    private final IAvansRepository avansRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserProfileService userProfileService;

    public AvansService(IAvansRepository avansRepository, JwtTokenProvider jwtTokenProvider, UserProfileService userProfileService) {
        super(avansRepository);
        this.avansRepository = avansRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userProfileService = userProfileService;
    }
    public PersonnelAvansResponseDto savePersonnelAvans(PersonnelAvansRequestDto dto){
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
            Avans avans= IAvansMapper.INSTANCE.fromPersonnelAvansRequestDtoToAvans(dto);
            save(avans);
            PersonnelAvansResponseDto personnelAvansResponseDto = PersonnelAvansResponseDto.builder()
                    .name(optionalUser.get().getName())
                    .surname(optionalUser.get().getSurname())
                    .avansDate(avans.getAvansDate())
                    .avansDescription(avans.getAvansDescription())
                    .avansTotal(avans.getAvansTotal())
                    .phone(optionalUser.get().getPhone())
                    .build();
            return personnelAvansResponseDto ;
        }else {
            throw new UserManagerException(ErrorType.ROLE_NOT_PERSONNEL);
        }
    }






}
