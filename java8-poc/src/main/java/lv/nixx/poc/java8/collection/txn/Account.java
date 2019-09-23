package lv.nixx.poc.java8.collection.txn;

import java.util.*;

import static lv.nixx.poc.java8.collection.txn.AccountType.*;

public class Account implements Comparable<Account>{

    private static final Comparator<AccountType> accountTypeComparator = AccountTypeComparator.forOrder(DEPOSIT, CURRENT, CARD);

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

    @Override
    public int compareTo(Account account) {
        Comparator<Account> comparator = Comparator.comparing(Account::getType, accountTypeComparator)
                .thenComparingInt(t -> t.getTxns().size());

        return comparator.compare(this, account);
    }
}
