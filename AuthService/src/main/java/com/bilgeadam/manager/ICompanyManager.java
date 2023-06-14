package com.bilgeadam.manager;

import com.bilgeadam.dto.request.ManagerCompanySaveRequestDto;
import com.bilgeadam.dto.request.RegisterManagerRequestDto;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

import static com.bilgeadam.constants.ApiUrls.*;
@FeignClient(url = "http://localhost:8070/api/v1/company", name = "auth-company")
public interface ICompanyManager {

    @GetMapping(GET_COMPANIES)
    public ResponseEntity<Set<GetCompanyResponseDto>> getCompanies();

    @PostMapping("manager-save-company")
    public ResponseEntity<Boolean> saveCompany(ManagerCompanySaveRequestDto dto);
}
