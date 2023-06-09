package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddIncomeRequestDto;
import com.bilgeadam.dto.request.AddOutcomeRequestDto;
import com.bilgeadam.repository.entity.CompanyProfit;
import com.bilgeadam.services.CompanyProfitService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bilgeadam.constant.ApiUrls.*;
import static com.bilgeadam.constant.ApiUrls.ADD_OUTCOME;

@RestController
@RequestMapping(COMPANY_PROFIT)
@RequiredArgsConstructor
public class CompanyProfitController {
    private final CompanyProfitService companyProfitService;
    @PostMapping(ADD_INCOME)
    public ResponseEntity<CompanyProfit> addIncome(AddIncomeRequestDto dto){
        return ResponseEntity.ok(companyProfitService.addIncome(dto));
    }
    @PostMapping(ADD_OUTCOME)
    public ResponseEntity<CompanyProfit> addOutcome(AddOutcomeRequestDto dto){
        return ResponseEntity.ok(companyProfitService.addOutcome(dto));
    }
}
