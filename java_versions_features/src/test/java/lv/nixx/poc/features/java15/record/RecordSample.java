package lv.nixx.poc.features.java15.record;

import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class RecordSample {

    @Test
    public void sample() {

        Transaction t = new Transaction(100, BigDecimal.valueOf(100));

        System.out.println("Transaction (default: toString()):" + t);
        System.out.println("Transaction id:" + t.id());
        System.out.println("Transaction description:" + t.getDescription());

    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectTransactionSample() {
        new Transaction(1, ZERO);
    }

}

record Transaction(int id, BigDecimal amount) {

    Transaction {
        if (amount.compareTo(ZERO) == 0) {
            throw new IllegalArgumentException("Amount can't be zero");
        }
    }

    String getDescription() {
        return "Transaction id:" + id + "amount: " + amount;
    }

}
