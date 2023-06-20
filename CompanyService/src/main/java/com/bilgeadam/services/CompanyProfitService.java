package com.bilgeadam.services;

import com.bilgeadam.dto.request.AddIncomeRequestDto;
import com.bilgeadam.dto.request.AddOutcomeRequestDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.ICompanyProfitMapper;
import com.bilgeadam.repository.ICompanyProfitRepository;
import com.bilgeadam.repository.entity.CompanyProfit;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyProfitService extends ServiceManager<CompanyProfit,Long> {
    private final ICompanyProfitRepository companyProfitRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserProfileManager userProfileManager;
    public CompanyProfitService(ICompanyProfitRepository companyProfitRepository, JwtTokenProvider jwtTokenProvider, IUserProfileManager userProfileManager) {
        super(companyProfitRepository);
        this.companyProfitRepository = companyProfitRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userProfileManager = userProfileManager;
    }
    /**
     * frontend de şirket yöneticisinin şirkete gelir eklemesi için kullanılacak olan method
     * @param dto
     * @return
     */
    public CompanyProfit addIncome(AddIncomeRequestDto dto){// todo frontend de gelen datalar string olabilir ona göre dtoları refactor edebiliriz
        //tokenRoleControls(dto.getToken());
        CompanyProfit companyProfit = ICompanyProfitMapper.INSTANCE.toCompanyProfit(dto);
        return save(companyProfit);
    }

    public CompanyProfit addOutcome(AddOutcomeRequestDto dto){
        //tokenRoleControls(dto.getToken());
        CompanyProfit companyProfit = ICompanyProfitMapper.INSTANCE.toCompanyProfit(dto);
        return save(companyProfit);
    }
    public Double findIncomeByCompanyId(Long companyId){
        return companyProfitRepository.findSumOfIncomes(companyId);
    }
    public Double findOutcomeByCompanyId(Long companyId){
        return companyProfitRepository.findSumOfOutcomes(companyId);
    }
    /**
     * her metod da aynı kontrolleri tekrar yazmamak için ekledim
     * @param token
     * @return role dönüşü yapıyor
     */
    public Double companyTotalSalaries(String token){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.TOKEN_NOT_FOUND);// hata düzenlenebilir
        }
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        System.out.println(roles);
        if (roles.contains(ERole.MANAGER.toString())) {
            List<Double> salaries= userProfileManager.getEmployeeListforSalary(token).getBody();
            double totalSalary = 0.0;
            for(Double salary : salaries){
                if(salary != null)
                totalSalary += salary;
            }
            return totalSalary;
             }
        throw new CompanyManagerException(ErrorType.AUTHORIZATION_ERROR);

    }
    /**
     * her metod da aynı kontrolleri tekrar yazmamak için ekledim
     * @param token
     * @return authId dönüşü yapıyor
     */
    public Long tokenRoleControlsAndAuthId(String token){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.TOKEN_NOT_FOUND);// hata düzenlenebilir
        }
        List<String> roles = jwtTokenProvider.getRoleFromToken(token);
        if (!roles.contains(ERole.MANAGER)) {
            throw new CompanyManagerException(ErrorType.AUTHORIZATION_ERROR);
        }
        return authId.get();
    }
}
