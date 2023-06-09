package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.CompanyProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyProfitRepository extends JpaRepository<CompanyProfit,Long> {
    @Query("select sum (c.income) from CompanyProfit as c where c.companyId=?1")
    Double findSumOfIncomes(Long companyId);
    @Query("select sum (c.outcome) from CompanyProfit as c where c.companyId=?1")
    Double findSumOfOutcomes(Long companyId);
}
