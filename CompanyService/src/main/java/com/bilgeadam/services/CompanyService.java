package com.bilgeadam.services;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.CompanyMoneyOperationResponseDto;
import com.bilgeadam.dto.response.GetCompanyResponseDto;
import com.bilgeadam.dto.response.ProfitLossResponseDto;
import com.bilgeadam.dto.response.SaveCompanyResponseDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public SaveCompanyResponseDto saveCompany(SaveCompanyRequestDto dto){
            List<String> taxNumbers =  companyRepository.findTaxNumbers();
        if(taxNumbers.contains(dto.getTaxNumber())){
            throw new CompanyManagerException(ErrorType.COMPANY_DUPLICATE);
        }else{
            Company company = ICompanyMapper.INSTANCE.fromSaveCompanyResponseDtoToCompany(dto);
            save(company);
            SaveCompanyResponseDto responseDto = ICompanyMapper.INSTANCE.fromCompanytoSaveCompanyResponseDto(company);
                return responseDto;
            }
        }
        //authtan gelen companyname ve taxnumber kayıt edilmesi
    public Boolean saveCompany(ManagerCompanySaveRequestDto dto){
        Company company = ICompanyMapper.INSTANCE.fromManagerCompanySaveRequestDtoToCompany(dto);
        save(company);
        return true;
    }

    public Boolean deleteCompanyById(DeleteCompanyRequestDto dto) {
        //companyProfitService.tokenRoleControls(dto.getToken());
        deleteById(dto.getCompanyId());
        return true;
    }

    public ProfitLossResponseDto profitLoss(ProfitLossRequestDto dto) {// frontend de çağırılacak şirket karı nı hesaplayan method
        //companyProfitService.tokenRoleControls(dto.getToken());
        Double income = companyProfitService.findIncomeByCompanyId(dto.getCompanyId());
        Double outcome = companyProfitService.findOutcomeByCompanyId(dto.getCompanyId());
        Double total = income - outcome;
        // çalışan maaşlarının toplamı çekilecek aylık olarak bunu 12x çarpım
        // eksi olarak incomdan çıkarılacak
        return ICompanyMapper.INSTANCE.toProfitLossDto(income, outcome, total);
    }

    public List<GetCompanyResponseDto> getCompany() {
        List<GetCompanyResponseDto> responseDtoList = new ArrayList<>();
        List<String[]> companyNamesAndUrls = companyRepository.findCompanyNames();

        List<Company> companyList=findAll();
        System.out.println(companyList);
        for (String[] item : companyNamesAndUrls) {
            responseDtoList.add(GetCompanyResponseDto.builder()
                    .companyName(item[1])
                    .companyLogoUrl(item[2])
                    //.companyDirectories(userProfileManager.findByCompanyName(item[0]).getBody()) // managerdan şirket direktörünün geldiği kısım
                    .build());
        }
        return responseDtoList;
    }
    public List<GetCompanyResponseDto> getCompanyList() {
        List<GetCompanyResponseDto> responseDtoList = new ArrayList<>();
        List<Company> companyList = findAll();
        System.out.println(companyList);
        for (Company company : companyList) {
            responseDtoList.add(GetCompanyResponseDto.builder()
                            .companyId(company.getCompanyId())
                            .companyName(company.getCompanyName())
                            .companyLogoUrl(company.getCompanyLogoUrl())
                    .build());
        }
        System.out.println(responseDtoList);
        return responseDtoList;
    }

    //ıncome utcome profitloss payment response dönen, parametrede
    //token ,içeren ve o token ile userprofile istek atıp gelen veri ile
    //userın içinden companyıd cekip onuda companyıd ye göre şirketlerin ıncome
    //outcome profitloss paymenn bilgilerini çekip responsemapper ile dönmek.
    //ıncome bölü outcome ile profiltloss hesapla, eger personel ekleme çalışıyorsa
    //persnolleri çek ve ondanda maaşlarını topla paymente set et.
    public CompanyMoneyOperationResponseDto companyMoneyOperation(CompanyMoneyOperationRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.TOKEN_NOT_FOUND);
        }
        Optional<Company> company=companyRepository.findOptionalByAuthId(authId.get());
        Double incomeMoney = company.get().getIncome();
        Double outcomeMoney = company.get().getOutcome();
        Double karZararOrani=(incomeMoney/outcomeMoney)*100;
        company.get().setProfitLoss(karZararOrani);
        System.out.println(incomeMoney+" "+outcomeMoney+" "+karZararOrani);
        update(company.get());
        CompanyMoneyOperationResponseDto companyMoneyOperationResponseDto=ICompanyMapper.INSTANCE.fromCompanyToCompanyMoneyOperationRequestDto(company.get());
        System.out.println(companyMoneyOperationResponseDto);
        return companyMoneyOperationResponseDto;
    }

    }