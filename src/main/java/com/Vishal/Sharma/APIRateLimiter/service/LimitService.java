package com.Vishal.Sharma.APIRateLimiter.service;

import com.Vishal.Sharma.APIRateLimiter.entity.Limit;
import com.Vishal.Sharma.APIRateLimiter.entity.User;
import com.Vishal.Sharma.APIRateLimiter.exception.AccessLimitExceededException;
import com.Vishal.Sharma.APIRateLimiter.repository.LimitRepository;
import com.Vishal.Sharma.APIRateLimiter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitService {

    private final LimitRepository limitRepository;
    private final UserRepository userRepository;


    @Value("${limit.allowed.apiLimit}")
    private int allowedApiLimit;

    @Value("${limit.allowed.databaseLimit}")
    private int allowedDatabaseLimit;

    @Value("${limit.allowed.anonymousLimit}")
    private int allowedAnonymousLimit;

    @Value("${message.access.api}")
    private String apiAccessExceededMsg;

    @Value("${message.access.database}")

    private String databaseAccessExceededMsg;
    @Value("${message.access.anonymous}")
    private String anonymousAccessExceededMsg;


    public Limit createNewLimit() {
        Limit newLimit = Limit.builder().apiLimit(allowedApiLimit).dataBaseLimit(allowedDatabaseLimit).anonymousLimit(allowedAnonymousLimit).build();

        limitRepository.save(newLimit);
        return newLimit;
    }

    public void checkTillApiLimit(String userName) {
        User userInDb = userRepository.findByUserName(userName);
        int apiLimit = userInDb.getLimit().getApiLimit();
        if (apiLimit > 0) {
            userInDb.getLimit().setApiLimit(apiLimit - 1);
            userRepository.save(userInDb);
            limitRepository.save(userInDb.getLimit());
            return;
        }
        throw new AccessLimitExceededException(apiAccessExceededMsg);
    }

    public void checkTillDataBaseLimit(String userName) {
        User userInDb = userRepository.findByUserName(userName);
        int apiLimit = userInDb.getLimit().getApiLimit();
        int dataBaseLimit = userInDb.getLimit().getDataBaseLimit();
        if (apiLimit > 0) {
            if (dataBaseLimit > 0) {
                userInDb.getLimit().setDataBaseLimit(dataBaseLimit - 1);
                userInDb.getLimit().setApiLimit(apiLimit - 1);
                userRepository.save(userInDb);
                limitRepository.save(userInDb.getLimit());
                return;
            }
            throw new AccessLimitExceededException(databaseAccessExceededMsg);
        }
        throw new AccessLimitExceededException(apiAccessExceededMsg);
    }

    public void checkTillAnonymousAccessLimit(String userName) {
        User userInDb = userRepository.findByUserName(userName);
        int apiLimit = userInDb.getLimit().getApiLimit();
        int anonymouseLimit = userInDb.getLimit().getAnonymousLimit();
        if (apiLimit > 0) {
            if (anonymouseLimit > 0) {
                userInDb.getLimit().setAnonymousLimit(anonymouseLimit - 1);
                userInDb.getLimit().setApiLimit(apiLimit - 1);
                userRepository.save(userInDb);
                limitRepository.save(userInDb.getLimit());
                return;
            }
            throw new AccessLimitExceededException(anonymousAccessExceededMsg);
        }
        throw new AccessLimitExceededException(apiAccessExceededMsg);
    }
}

