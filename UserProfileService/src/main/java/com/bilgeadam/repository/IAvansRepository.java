package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Avans;
import com.bilgeadam.repository.enums.EAvans;
import com.bilgeadam.repository.enums.EStatusAvans;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAvansRepository extends MongoRepository<Avans,String> {

    List<Avans> findByAvansAndStatusAvans(EAvans avans, EStatusAvans statusAvans);


}
