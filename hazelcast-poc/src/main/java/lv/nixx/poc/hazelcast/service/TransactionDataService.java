package lv.nixx.poc.hazelcast.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.AbstractEntryProcessor;
import lv.nixx.poc.hazelcast.model.State;
import lv.nixx.poc.hazelcast.model.Transaction;

import java.math.BigDecimal;
import java.util.*;

// https://docs.hazelcast.org/docs/latest-dev/manual/html-single/index.html#entry-processor

public class TransactionDataService {

    private IMap<String, Map<String, Transaction>> remoteMap;

    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        remoteMap = hazelcastInstance.getMap("txn.test");
    }

    public Transaction add(Transaction txn) {
        String accountId = txn.getAccountId();
        return (Transaction) remoteMap.executeOnKey(accountId, new TransactionAdd(txn));
    }

    public Transaction update(Transaction txn) {
        String accountId = txn.getAccountId();
        return (Transaction) remoteMap.executeOnKey(accountId, new TransactionUpdater(txn));
    }

    public Transaction deleteTransaction(String accountId, String txnId) {
        return (Transaction) remoteMap.executeOnKey(accountId, new TransactionRemover(txnId));
    }

    public Transaction changeState(String accountId, String txnId, State state) {

        return (Transaction) remoteMap.executeOnKey(accountId, new AbstractEntryProcessor<String, Map<String, Transaction>>() {
            @Override
            public Object process(Map.Entry<String, Map<String, Transaction>> entry) {
                Map<String, Transaction> value = entry.getValue();
                if (value == null) {
                    return null;
                }

                Transaction changedTxn = value.computeIfPresent(txnId, (k, v) -> {
                    v.setState(state);
                    return v;
                });

                entry.setValue(value);
                return changedTxn;
            }
        });

    }

    public Map<String, Map<String, Transaction>> recalculateInterests() {

        Map<String, Map<String, Transaction>> globalMap = new HashMap<>();

        remoteMap.executeOnEntries(new AbstractEntryProcessor<String, Map<String, Transaction>>() {
            @Override
            public Object process(Map.Entry<String, Map<String, Transaction>> entry) {

                Map<String, Transaction> map = entry.getValue();
                if (map == null) {
                    return null;
                }

                map.values().forEach(t -> {
                    BigDecimal amount = t.getAmount();
                    t.setInterest(amount.multiply(BigDecimal.valueOf(0.1)));
                });

                entry.setValue(map);

                Map<String, Map<String, Transaction>> res = new HashMap<>();
                res.put(entry.getKey(), map);

                globalMap.putAll(res);

                return null;
            }
        });

        return globalMap;
    }

    public Transaction getTransaction(String accountId, String txnId) {
        return Optional.ofNullable(remoteMap.get(accountId))
                .map(t -> t.get(txnId))
                .orElse(null);
    }

    public Map<String, Map<String, Transaction>> getAllData() {
        return remoteMap;
    }

    public void clearAll() {
        remoteMap.clear();
    }


    class TransactionAdd extends AbstractEntryProcessor<String, Map<String, Transaction>> {

        private Transaction txn;

        TransactionAdd(Transaction txn) {
            this.txn = txn;
        }

        @Override
        public Transaction process(Map.Entry<String, Map<String, Transaction>> entry) {

            Map<String, Transaction> map = Optional.ofNullable(entry.getValue()).orElse(new HashMap<>());
            final String txnId = txn.getTxnId();
            map.put(txnId, txn);

            entry.setValue(map);
            return txn;
        }
    }


    class TransactionUpdater extends AbstractEntryProcessor<String, Map<String, Transaction>> {

        private Transaction txn;

        TransactionUpdater(Transaction txn) {
            this.txn = txn;
        }

        @Override
        public Transaction process(Map.Entry<String, Map<String, Transaction>> entry) {

            Map<String, Transaction> map = entry.getValue();
            if (map == null) {
                return null;
            }

            Transaction transaction = map.computeIfPresent(txn.getTxnId(), (k, v) -> txn);
            entry.setValue(map);

            return transaction;
        }
    }

    class TransactionRemover extends AbstractEntryProcessor<String, Map<String, Transaction>> {

        private String txnId;

        TransactionRemover(String txnId) {
            this.txnId = txnId;
        }

        @Override
        public Transaction process(Map.Entry<String, Map<String, Transaction>> entry) {
            Map<String, Transaction> value = entry.getValue();
            if (value == null) {
                return null;
            }

            entry.setValue(value);
            return value.remove(txnId);
        }
    }

}
