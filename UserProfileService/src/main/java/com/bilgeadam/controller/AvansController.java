package com.bilgeadam.controller;

import com.bilgeadam.dto.request.PersonnelAvansRequestDto;
import com.bilgeadam.dto.response.PersonnelAvansResponseDto;
import com.bilgeadam.repository.entity.Avans;
import com.bilgeadam.repository.entity.UserProfile;
import com.bilgeadam.service.AvansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequestMapping(AVANS)
@RequiredArgsConstructor
public class AvansController {

    private final AvansService avansService;

    @PostMapping(PERSONNEL_AVANS)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<PersonnelAvansResponseDto> savePersonnelAvans(@RequestBody PersonnelAvansRequestDto dto){
        return ResponseEntity.ok(avansService.savePersonnelAvans(dto));
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<Avans>> findAll(){
        return ResponseEntity.ok(avansService.findAll());
    }

    @PutMapping(MANAGER_AVANS_STATUS_CHECK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> managerAvansStatusCheck(String token,String avansId) {
        return ResponseEntity.ok(avansService.managerAvansStatusCheck(token,avansId));
    }
    @PutMapping(MANAGER_AVANS_STATUS_CROSS)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Boolean> managerAvansStatusCross(String token,String avansId) {
        return ResponseEntity.ok(avansService.managerAvansStatusCross(token,avansId));
    }

    @GetMapping("/avans-status-avans-pending")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Avans>> findAvansAndStatusAvansPending(){
        return ResponseEntity.ok(avansService.findAvansAndStatusAvansPending());
    }

}
