package lv.nixx.poc.java8.collection.txn;

import java.util.*;

public class Account {

    private String id;
    private List<Transaction> txn;
    private AccountType type;

    public Account(String id, List<Transaction> txn) {
        this.id = id;
        this.txn = txn;
    }

    public Account(String id, List<Transaction> txn, AccountType type) {
        this(id, txn);
        this.type = type;
    }

    public Collection<Transaction> getTxns() {
        return txn;
    }

    public AccountType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
