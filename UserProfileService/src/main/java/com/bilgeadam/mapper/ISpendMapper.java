package com.bilgeadam.mapper;


import com.bilgeadam.dto.request.PersonnelSpendRequestDto;

import com.bilgeadam.repository.entity.Spend;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ISpendMapper {
    ISpendMapper INSTANCE= Mappers.getMapper(ISpendMapper.class);

    //avans için mapper;
    //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    //UserProfile fromPersonnelAvansRequestDtoToUserProfile(final PersonnelAvansRequestDto dto, @MappingTarget UserProfile userProfile);

    Spend fromPersonnelSpendRequestDtoToSpend(final PersonnelSpendRequestDto dto);
    //PersonnelAvansResponseDto fromAvansToPersonnelAvansResponseDto(final Avans avans);



}
