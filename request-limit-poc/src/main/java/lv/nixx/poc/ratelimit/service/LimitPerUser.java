package lv.nixx.poc.ratelimit.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

enum LimitPerUser {


    READ_ONLY(2L),
    POWER_USER(5L);

    private long tokens;

    LimitPerUser(long tokens) {
        this.tokens = tokens;
    }

    public Bandwidth getLimit() {
        return Bandwidth.classic(tokens, Refill.intervally(tokens, Duration.ofMinutes(1)));
    }


    public static LimitPerUser getByUser(String user) {
        return user.equalsIgnoreCase("POWER") ? POWER_USER : READ_ONLY;
    }

}
