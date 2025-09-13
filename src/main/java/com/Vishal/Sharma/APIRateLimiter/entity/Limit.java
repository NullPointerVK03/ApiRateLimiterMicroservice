package com.Vishal.Sharma.APIRateLimiter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "limits")
public class Limit {
    @Id
    private ObjectId id;
    private int apiLimit;
    private int dataBaseLimit;
    private int anonymousLimit;

    public void reset(Limit limit) {
        this.apiLimit = limit.apiLimit;
        this.dataBaseLimit = limit.dataBaseLimit;
        this.anonymousLimit = limit.anonymousLimit;
    }
}
