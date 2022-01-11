package lv.nixx.poc.sandbox.collection.txn;

import lombok.Getter;
import lv.nixx.poc.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticByAccountUsingTeeingSample {

    // This feature is available from Java 12

    @Test
    public void statisticByAccountSample() throws ParseException {

        Collection<Transaction> txns = List.of(
                new Transaction("id", BigDecimal.valueOf(10.10), "ACC1", "GBP", getDate("01.03.2016")),
                new Transaction("id", BigDecimal.valueOf(7.77), "ACC1", "GBP", getDate("01.03.2016")),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.08.2016")),
                new Transaction("id3", BigDecimal.valueOf(1.25), "ACC2", "EUR", getDate("01.10.2016")),
                new Transaction("id3", BigDecimal.valueOf(3.75), "ACC2", "EUR", getDate("03.10.2016")),
                new Transaction("id31", BigDecimal.valueOf(5.8), "ACC2", "USD", getDate("02.10.2016")),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR", getDate("01.12.2016"))
        );

        var amountComparator = Comparator.comparing(Transaction::getAmount);

        Map<String, TxnStatistics> txnStatistics = txns.stream()
                .collect(
                        Collectors.groupingBy(Transaction::getAccount,
                                Collectors.teeing(
                                        Collectors.minBy(amountComparator),
                                        Collectors.maxBy(amountComparator),
                                        (min, max) -> new TxnStatistics(
                                                min.orElse(null),
                                                max.orElse(null)
                                        )
                                ))
                );

        txnStatistics.forEach((key, value) -> {
            System.out.println(key);
            System.out.println("\t" + value);
        });

    }

    @Getter
    static class TxnStatistics {
        private final Transaction min;
        private final Transaction max;

        public TxnStatistics(Transaction min, Transaction max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return "min = " + min.getAmount() + ": max = " + max.getAmount();
        }
    }

    private Date getDate(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }
}
