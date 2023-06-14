package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.AuthCreatePersonnelProfileResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth fromVisitorsRequestDtoToAuth(final RegisterVisitorRequestDto dto);
    NewCreateVisitorUserRequestDto fromAuthNewCreateVisitorUserRequestDto(final Auth auth);
    NewCreateAdminUserRequestDto fromAuthNewCreateAdminUserRequestDto(final Auth auth);
    Auth fromManagerRequestDtoToAuth(final RegisterManagerRequestDto dto);
    NewCreateManagerUserRequestDto fromRegisterManagerRequestDtoToNewCreateManagerUserRequestDto(final RegisterManagerRequestDto dto);

    RegisterResponseDto fromAuthToRegisterResponseDto(final Auth auth);

    Auth fromCreatePersonelProfileDtotoAuth(final AuthCreatePersonnelProfileResponseDto dto);

    CreateUserRequestDto fromAuthToCreateUserRequestDto(final Auth auth);
    MailRegisterModel fromAuthToMailRegisterModel(final Auth auth);
    Auth toUserAuth(RegisterVisitorRequestDto dto);

    //company manager save için dönüşüm
    ManagerCompanySaveRequestDto fromAuthToCompanyManagerSaveRequestDto(final Auth auth);

}
