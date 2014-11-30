package com.mlk007.mngcms.repository;

import com.mlk007.mngcms.domain.Cabinet;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Cabinet entity.
 */
public interface CabinetRepository extends MongoRepository<Cabinet, String> {

}
