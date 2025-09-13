package com.Vishal.Sharma.APIRateLimiter.schedular;

import com.Vishal.Sharma.APIRateLimiter.entity.Limit;
import com.Vishal.Sharma.APIRateLimiter.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResetLimitService {

    private final LimitRepository limitRepository;

    @Value("${limit.allowed.apiLimit}")
    private int allowedApiLimit;

    @Value("${limit.allowed.databaseLimit}")
    private int allowedDatabaseLimit;

    @Value("${limit.allowed.anonymousLimit}")
    private int allowedAnonymousLimit;

    public ResetLimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void resetAccessLimit() {
        try {
//            ste
            List<Limit> limits = limitRepository.findAll();
            Limit standardLimit = Limit.builder()
                    .apiLimit(allowedApiLimit)
                    .dataBaseLimit(allowedDatabaseLimit)
                    .anonymousLimit(allowedAnonymousLimit)
                    .build();

            limits.forEach(limit -> {
                limit.reset(standardLimit);
                limitRepository.save(limit);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
