package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import com.bilgeadam.dto.response.ProfitLossResponseDto;
import com.bilgeadam.dto.response.SaveCompanyResponseDto;
import com.bilgeadam.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;
@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    @PostMapping(CREATE)
    public ResponseEntity<SaveCompanyResponseDto> createCompany(SaveCompanyRequestDto dto){
        return ResponseEntity.ok(companyService.saveCompany(dto));
    }
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> deleteCompany(DeleteCompanyRequestDto dto){
        return ResponseEntity.ok(companyService.deleteCompanyById(dto));
    }
    @GetMapping(PROFIT_LOSS)
    public ResponseEntity<ProfitLossResponseDto> profitLoss(ProfitLossRequestDto dto){
        return ResponseEntity.ok(companyService.profitLoss(dto));
    }
    @GetMapping(GET_COMPANIES)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<GetCompanyResponseDto>> getCompanies(){
        return ResponseEntity.ok(companyService.getCompany());
    }



}
