package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.ManagerCompanySaveRequestDto;
import com.bilgeadam.dto.request.SaveCompanyRequestDto;
import com.bilgeadam.dto.response.CompanyMoneyOperationResponseDto;
import com.bilgeadam.dto.response.ProfitLossResponseDto;
import com.bilgeadam.dto.response.SaveCompanyResponseDto;
import com.bilgeadam.repository.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ICompanyMapper {
    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);

    Company toCompany(final SaveCompanyRequestDto dto);
    ProfitLossResponseDto toProfitLossDto(Double totalIncome, Double totalOutcome, Double companyTotalProfit);

    Company fromSaveCompanyResponseDtoToCompany(final SaveCompanyRequestDto dto);

    SaveCompanyResponseDto fromCompanytoSaveCompanyResponseDto(final Company company);

    Company fromManagerCompanySaveRequestDtoToCompany(final ManagerCompanySaveRequestDto dto);

    CompanyMoneyOperationResponseDto fromCompanyToCompanyMoneyOperationRequestDto(final Company company);
}
