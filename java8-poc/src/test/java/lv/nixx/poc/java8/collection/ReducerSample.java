package lv.nixx.poc.java8.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ReducerSample {

    @Test
    public void reducerSample() throws Exception {

        Collection<Posting> postings = Arrays.asList(
                new Posting("post1", toTimestamp("04/26/2019 12:00:01"), toDate("04/26/2019"), BigDecimal.valueOf(10.01)),
                new Posting("post1", toTimestamp("04/26/2019 12:00:02"), toDate("04/26/2019"), BigDecimal.valueOf(20.01)),

                new Posting("post1", toTimestamp("04/25/2019 12:00:03"), toDate("04/25/2019"), BigDecimal.valueOf(30.01)),

                new Posting("post2", toDate("04/25/2019"), toTimestamp("04/26/2019 12:00:01"), BigDecimal.valueOf(30.03)),

                new Posting("post3", toDate("04/25/2019"), toTimestamp("04/26/2019 12:00:01"), BigDecimal.valueOf(33.33))
        );

        Collection<Collection<Optional<Posting>>> collect = postings.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(Posting::getId,
                                        Collectors.collectingAndThen(
                                                Collectors.groupingBy(Posting::getSettleDate,
                                                        Collectors.maxBy(Comparator.comparing(Posting::getTimestamp))
                                                ), Map::values
                                        )
                                ), Map::values)
                );


        Collection<Optional<Posting>> result = collect.stream()
                .flatMap(Collection::stream)
                .map(x -> x.orElse(null))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(Posting::getId, Collectors.reducing(this::calculateBalance))
                                , Map::values
                        )
                );

        result.forEach(System.out::println);

    }

    private Posting calculateBalance(Posting t, Posting t1) {
        t.setBalance(t.getBalance().add(t1.getBalance()));
        return t;
    }


    private Date toDate(String date) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(date);
    }

    private Date toTimestamp(String timestamp) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(timestamp);
    }


    @Data
    @ToString
    @AllArgsConstructor
    class Posting {
        String id;
        Date timestamp;
        Date settleDate;
        BigDecimal balance;
    }

}
