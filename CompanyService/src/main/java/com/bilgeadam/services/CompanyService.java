package com.bilgeadam.services;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.repository.ICompanyPageRepository;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ICompanyPageRepository companyPageRepository;

    public CompanyService(ICompanyRepository companyRepository, JwtTokenProvider jwtTokenProvider, CompanyProfitService companyProfitService, IUserProfileManager userProfileManager, ICompanyPageRepository companyPageRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.companyProfitService = companyProfitService;
        this.userProfileManager = userProfileManager;
        this.companyPageRepository = companyPageRepository;
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
    //rolü yalnıza manager olanlar yapabilir diye kontrol etmek gerekir token role verisin cekerek
    public Boolean update(UpdateCompanyRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.TOKEN_NOT_FOUND);
        }
        Optional<Company> optionalCompany=companyRepository.findOptionalByAuthId(authId.get());
        System.out.println(optionalCompany);
        if(optionalCompany.isEmpty()){
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.MANAGER.toString())){
            Company company = ICompanyMapper.INSTANCE.fromUpdateCompanyResponseDtoToCompany(dto,optionalCompany.get());
            System.out.println(company);
            update(company);
            return true;
        }else {
            throw new CompanyManagerException(ErrorType.ROLE_NOT_MANAGER);
        }
    }
    //authtan gelen companyname ve taxnumber kayıt edilmesi
    public Boolean companySave(ManagerCompanySaveRequestDto dto){
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

    public List<Company> companySearch(String text){
        List<Company> company = companyRepository.findByCompanyNameStartingWithIgnoreCase(text);
        System.out.println(company);
        return company;
    }
    public Page<Company> companyPageSearch(String text, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1, 5); // pageNumber - 1 because pages are zero based index
        Page<Company> company = companyPageRepository.findByCompanyNameStartingWithIgnoreCase(text, pageable);
        System.out.println(company.getContent());
        return company;
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
        System.out.println(company);
        Double incomeMoney = company.get().getIncome();
        Double outcomeMoney = company.get().getOutcome();
        Double karZararOrani=((incomeMoney-outcomeMoney)/incomeMoney)*100;
        company.get().setProfitLoss(karZararOrani);
        company.get().setPayments(20000D);
        System.out.println(incomeMoney+" "+outcomeMoney+" "+karZararOrani);
        update(company.get());
        CompanyMoneyOperationResponseDto companyMoneyOperationResponseDto=ICompanyMapper.INSTANCE.fromCompanyToCompanyMoneyOperationRequestDto(company.get());
        System.out.println(companyMoneyOperationResponseDto);
        return companyMoneyOperationResponseDto;
    }

    public GetCompanyResponseDto getCompanyWithId(Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if (company == null) {
            // Şirket bulunamadı durumunda uygun bir hata işleme mekanizması ekleyebilirsiniz.
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }

        GetCompanyResponseDto responseDto = GetCompanyResponseDto.builder()
                .companyId(company.get().getCompanyId())
                .companyName(company.get().getCompanyName())
                .companyLogoUrl(company.get().getCompanyLogoUrl())
                //.companyDirectories(userProfileManager.findByCompanyName(company.getCompanyName()).getBody())
                .build();

        return responseDto;
    }
    public InfoCompanyResponseDto infoProfileCompany(InfoCompanyRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
        System.out.println(authId);
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Company> optionalCompany = companyRepository.findOptionalByAuthId(authId.get());
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        List<String> role = jwtTokenProvider.getRoleFromToken(dto.getToken());
        if (role.contains(ERole.MANAGER.toString())){
            InfoCompanyResponseDto infoCompany=ICompanyMapper.INSTANCE.fromCompanyToCompanyInfoResponseDto(optionalCompany.get());
            return infoCompany;
        }else {
            throw new CompanyManagerException(ErrorType.ROLE_NOT_MANAGER);
        }
    }




}