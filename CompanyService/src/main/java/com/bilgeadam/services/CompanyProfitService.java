package com.bilgeadam.services;

import com.bilgeadam.dto.request.AddIncomeRequestDto;
import com.bilgeadam.dto.request.AddOutcomeRequestDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.ICompanyProfitMapper;
import com.bilgeadam.repository.ICompanyProfitRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.repository.entity.CompanyProfit;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyProfitService extends ServiceManager<CompanyProfit,Long> {
    private final ICompanyProfitRepository companyProfitRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public CompanyProfitService(ICompanyProfitRepository companyProfitRepository, JwtTokenProvider jwtTokenProvider) {
        super(companyProfitRepository);
        this.companyProfitRepository = companyProfitRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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
    public String tokenRoleControls(String token){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.TOKEN_NOT_FOUND);// hata düzenlenebilir
        }
        Optional<String> role = jwtTokenProvider.getRoleFromToken(token);
        if (!role.get().equals(ERole.DIRECTORY)) {
            throw new CompanyManagerException(ErrorType.AUTHORIZATION_ERROR);
        }
        return role.get();
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
        Optional<String> role = jwtTokenProvider.getRoleFromToken(token);
        if (!role.get().equals(ERole.DIRECTORY)) {
            throw new CompanyManagerException(ErrorType.AUTHORIZATION_ERROR);
        }
        return authId.get();
    }
}
