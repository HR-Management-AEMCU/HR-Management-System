package com.bilgeadam.services;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import com.bilgeadam.dto.response.ProfitLossResponseDto;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService extends ServiceManager<Company,Long> {
    private final ICompanyRepository companyRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyProfitService companyProfitService;
    private final IUserProfileManager userProfileManager;

    public CompanyService(ICompanyRepository companyRepository, JwtTokenProvider jwtTokenProvider, CompanyProfitService companyProfitService, IUserProfileManager userProfileManager) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.companyProfitService = companyProfitService;
        this.userProfileManager = userProfileManager;
    }
    public Company createCompany(CreateCompanyRequestDto dto){
        //companyProfitService.tokenRoleControls(dto.getToken());
        return save(ICompanyMapper.INSTANCE.toCompany(dto));
    }
    public Boolean deleteCompanyById(DeleteCompanyRequestDto dto){
        //companyProfitService.tokenRoleControls(dto.getToken());
        deleteById(dto.getCompanyId());
        return true;
    }

    public ProfitLossResponseDto profitLoss(ProfitLossRequestDto dto){// frontend de çağırılacak şirket karı nı hesaplayan method
        //companyProfitService.tokenRoleControls(dto.getToken());
        Double income= companyProfitService.findIncomeByCompanyId(dto.getCompanyId());
        Double outcome= companyProfitService.findOutcomeByCompanyId(dto.getCompanyId());
        Double total = income-outcome;
        // çalışan maaşlarının toplamı çekilecek aylık olarak bunu 12x çarpım
        // eksi olarak incomdan çıkarılacak
        return ICompanyMapper.INSTANCE.toProfitLossDto(income,outcome,total);
    }
    public List<GetCompanyResponseDto> getCompany(){
        List<GetCompanyResponseDto> responseDtoList = new ArrayList<>();
        List<String[]> companyNamesAndUrls = companyRepository.findCompanyNames();

        for (String[] item : companyNamesAndUrls) {
            responseDtoList.add(GetCompanyResponseDto.builder()
                            .companyName(item[0])
                            .companyLogoUrl(item[1])
                            .companyDirectories(userProfileManager.findByCompanyName(item[0]).getBody()) // managerdan şirket direktörünün geldiği kısım
                    .build());
        }
        return responseDtoList;
    }
}
