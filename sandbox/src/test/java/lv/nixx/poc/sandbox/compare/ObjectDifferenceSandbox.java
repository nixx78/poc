package lv.nixx.poc.sandbox.compare;

import lv.nixx.poc.domain.Transaction;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class ObjectDifferenceSandbox {

    @Test
    public void findDifferenceTest() throws ParseException {

        Transaction t1 = new Transaction("id1", BigDecimal.valueOf(10.0), "acc1",
                "USD", "12.06.2022");

        Transaction t2 = new Transaction("id1", BigDecimal.valueOf(10.1), "acc1",
                "USD", "13.06.2022");

        DiffBuilder<Transaction> db = new DiffBuilder<>(t1, t2, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", t1.getId(), t2.getId())
                .append("amount", t1.getAmount(), t2.getAmount())
                .append("account", t1.getAccount(), t2.getAccount())
                .append("currency", t1.getCurrency(), t2.getCurrency())
                .append("date", t1.getLastUpdateDate(), t2.getLastUpdateDate());

        DiffResult<Transaction> diffResult = db.build();
        System.out.println("Difference [" + diffResult + "]");

        assertEquals(2, diffResult.getNumberOfDiffs());

        diffResult.getDiffs().forEach( System.out::println);
    }

    @Test
    public void compareTest() throws ParseException {

        Transaction t1 = new Transaction("id1", BigDecimal.valueOf(10.0), "acc1",
                "USD", "12.06.2022");

        Transaction t2 = new Transaction("id1", BigDecimal.valueOf(10.0), "acc1",
                "USD", "12.06.2022");

        Comparator<Transaction> txnComparator = Comparator.comparing(Transaction::getId)
                .thenComparing(Transaction::getAmount)
                .thenComparing(Transaction::getAccount)
                .thenComparing(Transaction::getCurrency)
                .thenComparing(Transaction::getLastUpdateDate);

        assertEquals(0, txnComparator.compare(t1, t2));

        Transaction t3 = new Transaction("id1", BigDecimal.valueOf(10.1), "acc1",
                "USD", "12.06.2022");

        assertEquals(-1, txnComparator.compare(t1, t3));
    }


}
