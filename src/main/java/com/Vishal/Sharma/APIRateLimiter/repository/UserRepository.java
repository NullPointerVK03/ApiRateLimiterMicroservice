package com.Vishal.Sharma.APIRateLimiter.repository;

import com.Vishal.Sharma.APIRateLimiter.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
}
