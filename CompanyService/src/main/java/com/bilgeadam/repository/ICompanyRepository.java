package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Long> {
/*    @Query("select new com.bilgeadam.repository.view.VwCompany(c.income,c.outcome) from Company as c")
    List<VwCompany> findIncomeAndOutcome();
    @Query("select c.income from Company as c")
    List<Double> findIncome();
    @Query("select sum (c.income) from Company as c")
    Double findSumOfIncomes();
    @Query("select sum (c.outcome) from Company as c")
    Double findSumOfOutcomes();*/
    @Query("select c.companyName, c.companyLogoUrl from Company as c")
    List<String[]> findCompanyNames();

    @Query("SELECT c.taxNumber FROM Company c")
    List<String> findTaxNumbers();

    @Query(value = "select t.company_id,t.company_name from tblcompany as t WHERE LOWER(t.company_name) LIKE LOWER(CONCAT('%', ?1, '%'))", nativeQuery = true)
    List<String> findByCompanyName(String text);
}
