package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EAvans;
import com.bilgeadam.repository.enums.EStatusAvans;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Spend extends Base {
    @Id
    private String spendId;
    private Double spendTotal;
    private String spendDescription;
    private Date spendDate;
    private String spendPhoto;
    @Builder.Default
    private EStatusAvans statusAvans=EStatusAvans.PENDING;
    @Builder.Default
    private EAvans avans=EAvans.SPEND;
    private String name;
    private String surname;
    private String phone;


}
