package com.jumbo.store.geo.repository;

import com.jumbo.store.geo.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends MongoRepository<Store, String> {
}