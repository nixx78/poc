package lv.nixx.poc.ratelimit.service;

import io.github.bucket4j.Bucket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserLimiterService {

    // User - Bucket for concrete user
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String user) {
        return cache.computeIfAbsent(user, this::newBucket);
    }

    private Bucket newBucket(String user) {
        LimitPerUser limit = LimitPerUser.getByUser(user);

        return Bucket.builder()
                .addLimit(limit.getLimit())
                .build();
    }

}
