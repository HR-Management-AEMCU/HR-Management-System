package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICompanyPageRepository extends PagingAndSortingRepository<Company, Long> {
    Page<Company> findByCompanyNameStartingWithIgnoreCase(String companyName, Pageable pageable);
}