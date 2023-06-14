package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.CompanyMoneyOperationResponseDto;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import com.bilgeadam.dto.response.ProfitLossResponseDto;
import com.bilgeadam.dto.response.SaveCompanyResponseDto;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.services.CompanyService;
import io.swagger.v3.oas.annotations.Hidden;
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
    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody UpdateCompanyRequestDto dto){
        return ResponseEntity.ok(companyService.update(dto));
    }
    //authtan gelen companyname ve taxnumber kayıt edilmesi
    @PostMapping("manager-save-company")
    @Hidden
    public ResponseEntity<Boolean> companySave(@RequestBody ManagerCompanySaveRequestDto dto){
        return ResponseEntity.ok(companyService.companySave(dto));
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
    @GetMapping(GET_COMPANIES+"-list")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<GetCompanyResponseDto>> getCompaniesList(){
        return ResponseEntity.ok(companyService.getCompanyList());
    }
    @GetMapping(FIND_ALL)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Company>> findAll(){
        return ResponseEntity.ok(companyService.findAll());
    }
    @PostMapping(COMPANY_MONEY_OPERATION)
    public ResponseEntity<CompanyMoneyOperationResponseDto> companyMoneyOperation(CompanyMoneyOperationRequestDto dto){
        return ResponseEntity.ok(companyService.companyMoneyOperation(dto));
    }

    @GetMapping(SEARCH_COMPANIES)
    public ResponseEntity<List<String>> searchCompany(String text){
        return ResponseEntity.ok(companyService.companySearch(text));
    }

}
