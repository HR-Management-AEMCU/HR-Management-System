package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.CreateEmployeeResponseDto;
import com.bilgeadam.dto.response.ListPersonnelResponseDto;
import com.bilgeadam.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE= Mappers.getMapper(IUserProfileMapper.class);

    UserProfile fromNewCreateVisitorUserResponseDtoToUserProfile(final NewCreateVisitorUserRequestDto dto);

    UserProfile fromNewCreateManagerUserResponseDtoToUserProfile(final NewCreateManagerUserRequestDto dto);
    //UserProfile createUserRequestDtoToUserProfile(final CreateUserRequestDto dto);
    UserProfile createEmployeeRequestDtoToUserProfile(final CreateEmployeeRequestDto dto);

    CreateEmployeeResponseDto userProfileToCreateEmployeeResponseDto(final UserProfile userProfile);

    UpdateManagerStatusRequestDto fromUserProfileToUpdateManagerStatusRequestDto(final UserProfile userProfile);

    //userdan listempployee döünşüm
    //ListPersonnelResponseDto fromUserProfileToListEmployeeResponseDto(final List<UserProfile> userProfile);
}
