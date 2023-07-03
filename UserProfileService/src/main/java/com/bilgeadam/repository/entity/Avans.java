package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.*;
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
public class Avans extends Base {
    @Id
    private String avansId;
    private Double avansTotal;
    private String avansDescription;
    private Date avansDate;
    @Builder.Default
    private EStatusAvans statusAvans=EStatusAvans.PENDING;
    @Builder.Default
    private EAvans avans=EAvans.AVANS;
    private String name;
    private String surname;
    private String phone;


}
