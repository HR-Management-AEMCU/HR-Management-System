package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateCompanyRequestDto;
import com.bilgeadam.dto.response.ProfitLossResponseDto;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ICompanyMapper {
    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);

    Company toCompany(final CreateCompanyRequestDto dto);
    ProfitLossResponseDto toProfitLossDto(Double totalIncome, Double totalOutcome, Double companyTotalProfit);
}
