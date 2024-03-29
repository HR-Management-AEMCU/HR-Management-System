package com.bilgeadam.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9070/api/v1/company", name = "userprofile-company",decode404 = true)
public interface ICompanyManager {

    //@PostMapping("/save-company")
    //public ResponseEntity<String> saveCompanyRequestDto(@RequestBody SaveCompanyRequestDto dto);

    //@GetMapping("/get-personnel-company-information/{companyId}")
    //public ResponseEntity<PersonnelCompanyInformationResponseDto> getPersonnelCompanyInformation(@PathVariable String companyId);
}
