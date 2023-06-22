package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.CreateEmployeeResponseDto;
import com.bilgeadam.dto.response.InfoManagerResponseDto;
import com.bilgeadam.dto.response.InfoPersonelResponseDto;
import com.bilgeadam.dto.response.InfoVisitorResponseDto;
import com.bilgeadam.rabbitmq.model.PersonnelPasswordModel;
import com.bilgeadam.repository.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE= Mappers.getMapper(IUserProfileMapper.class);

    UserProfile fromNewCreateVisitorUserResponseDtoToUserProfile(final NewCreateVisitorUserRequestDto dto);
    UserProfile fromNewCreateAdminUserResponseDtoToUserProfile(final NewCreateAdminUserRequestDto dto);

    UserProfile fromNewCreateManagerUserResponseDtoToUserProfile(final NewCreateManagerUserRequestDto dto);
    //UserProfile createUserRequestDtoToUserProfile(final CreateUserRequestDto dto);
    UserProfile createEmployeeRequestDtoToUserProfile(final CreateEmployeeRequestDto dto);

    CreateEmployeeResponseDto userProfileToCreateEmployeeResponseDto(final UserProfile userProfile);

    UpdateManagerStatusRequestDto fromUserProfileToUpdateManagerStatusRequestDto(final UserProfile userProfile);

    AuthCreatePersonnelProfileRequestDto fromUserProfileToAuthCreatePersonnelProfileRequestDto(final UserProfile userProfile);
    PersonnelPasswordModel fromUserProfileToPersonnelPasswordModel(final UserProfile userProfile);

    //updatevisitor metodu için
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile fromUpdateVisitorRequestDtoToUserProfile(final UpdateVisitorRequestDto dto, @MappingTarget UserProfile userProfile);
    //updatepersonel için
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile fromUpdatePersonnelRequestDtoToUserProfile(final UpdatePersonnelRequestDto dto, @MappingTarget UserProfile userProfile);
    //infoProfileVisitor için responseDto dönüşüm metodu
    InfoVisitorResponseDto fromUserPRofileToInfoVisitorResponseDto(final UserProfile userProfile);

    //userdan listempployee döünşüm
    //ListPersonnelResponseDto fromUserProfileToListEmployeeResponseDto(final List<UserProfile> userProfile);

    InfoPersonelResponseDto fromUserPRofileToInfoPersonelResponseDto(final UserProfile userProfile);
    InfoManagerResponseDto fromUserPRofileToInfoManagerResponseDto(final UserProfile userProfile);




}
