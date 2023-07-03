package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.Avans;
import com.bilgeadam.repository.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAvansMapper {
    IAvansMapper INSTANCE= Mappers.getMapper(IAvansMapper.class);

    //avans için mapper;
    //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //UserProfile fromPersonnelAvansRequestDtoToUserProfile(final PersonnelAvansRequestDto dto, @MappingTarget UserProfile userProfile);

    Avans fromPersonnelAvansRequestDtoToAvans(final PersonnelAvansRequestDto dto);
    PersonnelAvansResponseDto fromAvansToPersonnelAvansResponseDto(final Avans avans);



}
