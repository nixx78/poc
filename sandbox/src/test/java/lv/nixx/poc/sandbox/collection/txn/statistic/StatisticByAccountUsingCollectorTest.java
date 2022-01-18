package lv.nixx.poc.sandbox.collection.txn.statistic;

import lv.nixx.poc.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;

public class StatisticByAccountUsingCollectorTest {

    @Test
    public void statisticByAccountSample() throws ParseException {

        Collection<Transaction> txns = List.of(
                new Transaction("id", BigDecimal.valueOf(10.10), "ACC1", "GBP", "01.03.2016"),
                new Transaction("id", BigDecimal.valueOf(7.77), "ACC1", "GBP", "01.03.2016"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", "01.08.2016"),
                new Transaction("id3", BigDecimal.valueOf(1.25), "ACC2", "EUR", "01.10.2016"),
                new Transaction("id3", BigDecimal.valueOf(3.75), "ACC2", "EUR", "03.10.2016"),
                new Transaction("id31", BigDecimal.valueOf(5.8), "ACC2", "USD", "02.10.2016"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR", "01.12.2016")
        );

        List<String> collect = txns.stream()
                .collect(
                        collectingAndThen(
                                groupingBy(Transaction::getAccount,
                                        mapping(Transaction::getAmount, CollectionStat.collector())
                                ), t -> t.entrySet()
                                        .stream()
                                        .sorted(Map.Entry.comparingByKey())
                                        .map(e -> e.getKey() + ":" + e.getValue().toString())
                                        .collect(toList())
                        )
                );

        collect.forEach(System.out::println);

        assertThat(collect, containsInRelativeOrder(
                "ACC1:count:2 min:7.77 max:10.1",
                "ACC2:count:4 min:1.25 max:20.12",
                "ACC3:count:1 min:40.14 max:40.14"
                )
        );

    }
}
