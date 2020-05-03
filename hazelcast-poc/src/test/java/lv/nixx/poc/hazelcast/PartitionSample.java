package lv.nixx.poc.hazelcast;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.core.*;
import com.hazelcast.query.Predicates;
import lv.nixx.poc.hazelcast.model.Account;
import lv.nixx.poc.hazelcast.model.Transaction;
import lv.nixx.poc.hazelcast.model.TransactionKey;
import org.junit.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

public class PartitionSample {

    private HazelcastInstance hazelcastInstance = HazelcastTestInstance.get();

    private IMap<String, Account> accounts = hazelcastInstance.getMap("accounts.map");
    private IMap<TransactionKey, Transaction> transactions = hazelcastInstance.getMap("transactions.map");

    private String accountId = "accId.1";


    @Test
    public void addTransactionSample() throws ExecutionException, InterruptedException {

        accounts.put(accountId, new Account(accountId, "Name1"));

        transactions.put(new TransactionKey(accountId, "txnId.1"), new Transaction()
                .setAccountId(accountId)
                .setTxnId("txnId.1")
                .setAmount(BigDecimal.valueOf(100.00))
                .setCurrency("EUR")
        );

        transactions.put(new TransactionKey(accountId, "txnId.2"), new Transaction()
                .setAccountId(accountId)
                .setTxnId("txnId.2")
                .setAmount(BigDecimal.valueOf(200.00))
                .setCurrency("EUR")
        );

        transactions.put(new TransactionKey(accountId, "txnId.3"), new Transaction()
                .setAccountId(accountId)
                .setTxnId("txnId.3")
                .setAmount(BigDecimal.valueOf(300.00))
                .setCurrency("EUR")
        );

        IExecutorService executorService = hazelcastInstance.getExecutorService("ExecutorService");

        Transaction newTxn = new Transaction()
                .setAccountId(accountId)
                .setTxnId("txnId.4")
                .setAmount(BigDecimal.valueOf(400.00))
                .setCurrency("EUR");

        AddTxnTask task = new AddTxnTask(newTxn);

        Future<BigDecimal> future = executorService.submit(task);
        BigDecimal newBalance = future.get();

        assertThat(newBalance, comparesEqualTo(BigDecimal.valueOf(1000)));
    }

    @Test
    //https://docs.hazelcast.org/docs/latest-dev/manual/html-single/#data-partitioning
    public void partitionsSample() {
        final IMap<Object, Object> m1 = hazelcastInstance.getMap("m1@p.key");
        final IMap<Object, Object> m2 = hazelcastInstance.getMap("m2@p.key");

        System.out.println("M1:" + m1.getPartitionKey());
        System.out.println("M2:" + m2.getPartitionKey());
    }

    static class AddTxnTask implements Callable<BigDecimal>, PartitionAware<String>, Serializable, HazelcastInstanceAware {

        private final Transaction txn;
        HazelcastInstance hazelcastInstance;

        public AddTxnTask(Transaction txn) {
            this.txn = txn;
        }

        @Override
        public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
            this.hazelcastInstance = hazelcastInstance;
        }

        @Override
        public String getPartitionKey() {
            return txn.getAccountId();
        }

        @Override
        public BigDecimal call() {
            IMap<String, Account> accountMap = hazelcastInstance.getMap("accounts.map");
            IMap<TransactionKey, Transaction> txnMap = hazelcastInstance.getMap("transactions.map");

            String accountId = txn.getAccountId();
            accountMap.lock(accountId);
            txnMap.put(new TransactionKey(accountId, txn.getTxnId()), txn);
            BigDecimal total = txnMap.aggregate(Aggregators.bigDecimalSum("amount"),
                    Predicates.equal("accountId", accountId));
            accountMap.unlock(accountId);

            return total;
        }
    }

}
