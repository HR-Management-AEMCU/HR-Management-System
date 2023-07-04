package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Spend;
import com.bilgeadam.repository.enums.EAvans;
import com.bilgeadam.repository.enums.EStatusAvans;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISpendRepository extends MongoRepository<Spend,String> {

    List<Spend> findByAvansAndStatusAvans(EAvans avans, EStatusAvans statusAvans);


}
