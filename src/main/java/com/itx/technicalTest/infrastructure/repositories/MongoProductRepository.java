package com.itx.technicalTest.infrastructure.repositories;

import com.itx.technicalTest.infrastructure.data.entities.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MongoProductRepository extends MongoRepository<ProductEntity, String> {

}
