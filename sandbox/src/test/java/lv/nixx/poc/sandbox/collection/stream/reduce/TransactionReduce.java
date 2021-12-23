package lv.nixx.poc.sandbox.collection.stream.reduce;

import lv.nixx.poc.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;

public class TransactionReduce {

    @Test
    public void totalAmountByCurrency() {

        Collection<Transaction> set = List.of(
                new Transaction("id1", BigDecimal.valueOf(30.13), "account1", "EUR"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "account1", "USD"),
                new Transaction("id3", BigDecimal.valueOf(50), "account1", "USD"),
                new Transaction("id4", BigDecimal.valueOf(150), "account1", "GBP"),
                new Transaction("id5", BigDecimal.valueOf(450), "account1", "USD")
        );

        Map<String, BigDecimal> r = set.stream()
                .collect(
                        groupingBy(Transaction::getCurrency,
                                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                        )
                );

        r.entrySet().forEach(System.out::println);

        assertEquals(3, r.size());


        Map<String, Long> collect = set.stream()
                .collect(
                        groupingBy(Transaction::getCurrency, Collectors.counting()
                        )
                );

        collect = sortByValue(collect);
        assertThat(collect.values(), contains(3L, 1L, 1L));
    }

    static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());

        Comparator<Map.Entry<K, V>> c = Map.Entry.comparingByValue();
        list.sort(c.reversed());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
