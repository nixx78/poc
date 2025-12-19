package lv.nixx.poc.sandbox.events;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static lv.nixx.poc.sandbox.events.PositionSandbox.Tier.*;

public class PositionSandbox {

    @Test
    public void eventSandbox() {
        PostingsModel postingModels = new PostingsModel(new Application());

        postingModels.loadInitialPostings(List.of(
                new Posting("Pos1", BigDecimal.valueOf(10.00), Tier1),
                new Posting("Pos2", BigDecimal.valueOf(20.00), Tier2),
                new Posting("Pos3", BigDecimal.valueOf(30.00), Tier2),
                new Posting("Pos4", BigDecimal.valueOf(40.00), Tier3)
        ));


        postingModels.processEvent(new PostingEvent("Pos1", BigDecimal.valueOf(21.01)));
        System.out.println("====================");
        postingModels.processEvent(new PostingEvent("Pos2", BigDecimal.valueOf(05.01)));
        System.out.println("====================");
        postingModels.processEvent(new PostingEvent("Pos3", BigDecimal.valueOf(100.00)));
    }

    @Test
    public void groupAndMap() {

        Map<String, Map<Tier, Posting>> collect = List.of(
                        new Posting("Pos1", BigDecimal.valueOf(10.00), Tier1),
                        new Posting("Pos1", BigDecimal.valueOf(11.00), Tier2),
                        new Posting("Pos2", BigDecimal.valueOf(20.00), Tier2),
                        new Posting("Pos3", BigDecimal.valueOf(30.00), Tier2),
                        new Posting("Pos4", BigDecimal.valueOf(40.00), Tier3)
                ).stream()
                .collect(groupingBy(Posting::getPos, toMap(Posting::getTier, Function.identity())));

        System.out.println(collect);
    }

    @Setter
    static class Application {

        void postingChanged(Posting posting, Balance balance) {

            System.out.println("Changed Posting: " + posting);
            System.out.println("Balance by Tier (Postings): " + balance.getBalanceByTier());

            System.out.println("Total balance: " + balance.getTotal());
        }

    }


    static class PostingsModel {

        Map<String, Posting> postings = new HashMap<>();
        Application app;

        public PostingsModel(Application app) {
            this.app = app;
        }

        void loadInitialPostings(Collection<Posting> positions) {
            positions.forEach(p -> this.postings.put(p.getPos(), p));
        }

        void processEvent(PostingEvent event) {
            String pos = event.getPos();
            BigDecimal balance = event.getBalance();

            Posting p = postings.computeIfPresent(pos, (k, v) -> {
                v.setBalance(balance);
                return v;
            });


            Map<Tier, BigDecimal> balanceByTier = postings.values()
                    .stream()
                    .collect(
                            groupingBy(Posting::getTier,
                                    Collectors.reducing(BigDecimal.ZERO, Posting::getBalance, BigDecimal::add)
                            )
                    );

            app.postingChanged(p, new Balance(balanceByTier));
        }


    }

    @Data
    @ToString
    @AllArgsConstructor
    static class Balance {

        Map<Tier, BigDecimal> balanceByTier;

        BigDecimal getTotal() {
            return balanceByTier.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        }

    }


    @Data
    @ToString
    @EqualsAndHashCode(of = "pos")
    @AllArgsConstructor
    static class PositionEvent {
        String pos;
        BigDecimal balance;
    }

    @Data
    @ToString
    @EqualsAndHashCode(of = "pos")
    @AllArgsConstructor
    static
    class PostingEvent {
        String pos;
        BigDecimal balance;
    }

    @Data
    @ToString
    @EqualsAndHashCode(of = "pos")
    @AllArgsConstructor
    static class Posting {
        String pos;
        BigDecimal balance;
        Tier tier;
    }

    enum Tier {
        Tier1, Tier2, Tier3
    }

}
