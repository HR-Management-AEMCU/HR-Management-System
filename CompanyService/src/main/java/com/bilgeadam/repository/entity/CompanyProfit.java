package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblcompanyprofit")
public class CompanyProfit extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyProfitId;
    private Long companyId;
    private Double income;
    private Double outcome;
    private String comment;
}
