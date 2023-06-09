package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.AddIncomeRequestDto;
import com.bilgeadam.dto.request.AddOutcomeRequestDto;
import com.bilgeadam.repository.entity.CompanyProfit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ICompanyProfitMapper {
    ICompanyProfitMapper INSTANCE = Mappers.getMapper(ICompanyProfitMapper.class);
    CompanyProfit toCompanyProfit(final AddIncomeRequestDto dto);
    CompanyProfit toCompanyProfit(final AddOutcomeRequestDto dto);
}
