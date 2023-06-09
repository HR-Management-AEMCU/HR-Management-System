package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateUserRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth fromRegisterRequestDtoToAuth(final RegisterRequestDto dto);
    RegisterResponseDto fromAuthToRegisterResponseDto(final Auth auth);

    CreateUserRequestDto fromAuthToCreateUserRequestDto(final Auth auth);
    MailRegisterModel fromAuthToMailRegisterModel(final Auth auth);
    Auth toUserAuth(RegisterRequestDto dto);

}
