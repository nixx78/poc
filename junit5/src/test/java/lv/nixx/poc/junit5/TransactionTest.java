package lv.nixx.poc.junit5;

import lv.nixx.poc.junit5.service.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Transaction fields test")
@RunWith(JUnitPlatform.class)
public class TransactionTest {

    @Test
    @DisplayName("Test all Transaction fields")
    public void calculatorFullTest() {

        final Transaction t = new Transaction();
        t.setId("txn.id");
        t.setAccountId("account.id");

        assertAll("Transaction",
                () -> assertEquals("Txn id is incorrect", "txn.id", t.getId()),
                () -> assertEquals("Account id is incorrect", "account.id", t.getAccountId())
        );

    }


}

