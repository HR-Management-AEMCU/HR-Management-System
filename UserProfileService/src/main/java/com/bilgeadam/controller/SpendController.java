package com.bilgeadam.controller;

import com.bilgeadam.dto.request.PersonnelSpendRequestDto;
import com.bilgeadam.dto.response.PersonnelSpendResponseDto;

import com.bilgeadam.repository.entity.Spend;
import com.bilgeadam.service.SpendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;
@RestController
@RequestMapping(SPEND)
@RequiredArgsConstructor
public class SpendController {
    private final SpendService spendService;

    @PostMapping(PERSONNEL_SPEND)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<PersonnelSpendResponseDto> savePersonnelSpend(@RequestBody PersonnelSpendRequestDto dto){
        return ResponseEntity.ok(spendService.savePersonnelSpend(dto));
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<Spend>> findAll(){
        return ResponseEntity.ok(spendService.findAll());
    }
    @PutMapping(MANAGER_SPEND_STATUS_CHECK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> managerSpendStatusCheck(String token,String spendId) {
        return ResponseEntity.ok(spendService.managerSpendStatusCheck(token,spendId));
    }
    @PutMapping(MANAGER_SPEND_STATUS_CROSS)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> managerSpendStatusCross(String token,String spendId) {
        return ResponseEntity.ok(spendService.managerSpendStatusCross(token,spendId));
    }

    @GetMapping("/spend-status-pending")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Spend>> findAvansAndStatusAvansPending(){
        return ResponseEntity.ok(spendService.findAvansAndStatusAvansPending());
    }



}
