package com.Vishal.Sharma.APIRateLimiter.repository;

import com.Vishal.Sharma.APIRateLimiter.entity.Limit;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LimitRepository extends MongoRepository<Limit, ObjectId> {
}
