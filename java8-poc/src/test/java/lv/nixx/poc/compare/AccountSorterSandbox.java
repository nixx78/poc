package lv.nixx.poc.compare;

import lv.nixx.poc.java8.collection.txn.Account;
import lv.nixx.poc.java8.collection.txn.AccountType;
import lv.nixx.poc.java8.collection.txn.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import static lv.nixx.poc.java8.collection.txn.AccountType.*;

public class AccountSorterSandbox {

    @Test
    public void sortAccountsNaturalEnumOrder() {
        List<Account> sortedList = createAccounts().stream()
                .sorted(Comparator.comparing(Account::getType).thenComparingInt(t -> t.getTxns().size()))
                .collect(Collectors.toList());

        assertThat(sortedList.stream().map(Account::getId).collect(Collectors.toList()),
                contains("AccountID3", "AccountID2", "AccountID1", "AccountID4"));
    }

    @Test
    public void sortAccountsCustomEnumOrder() {

        Comparator<AccountType> accountTypeComparator = AccountTypeComparator.forOrder(DEPOSIT, CURRENT, CARD);
        Comparator<Account> comparator = Comparator.comparing(Account::getType, accountTypeComparator)
                .thenComparingInt(t -> t.getTxns().size());

        List<Account> sortedList = createAccounts().stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        System.out.println(sortedList);

        assertThat(sortedList.stream().map(Account::getId).collect(Collectors.toList()),
                contains("AccountID4", "AccountID1", "AccountID3", "AccountID2"));
    }

    private Collection<Account> createAccounts() {
        Account acc1 = new Account("AccountID1", Arrays.asList(
                new Transaction("_1txn1", BigDecimal.valueOf(40.00), "AccountID1", "USD")
        ), AccountType.CURRENT);

        Account acc2 = new Account("AccountID2", Arrays.asList(
                new Transaction("_2txn4", BigDecimal.valueOf(5.00), "AccountID2", "USD"),
                new Transaction("2txn2", BigDecimal.valueOf(10.00), "AccountID2", "USD"),
                new Transaction("_2txn4", BigDecimal.valueOf(5.00), "AccountID2", "USD")
        ), AccountType.CARD);

        Account acc3 = new Account("AccountID3", Arrays.asList(
                new Transaction("_3txn4", BigDecimal.valueOf(5.00), "AccountID2", "USD")
        ), AccountType.CARD);


        Account acc4 = new Account("AccountID4", Arrays.asList(
                new Transaction("_4txn1", BigDecimal.valueOf(40.00), "AccountID4", "USD"),
                new Transaction("_4txn2", BigDecimal.valueOf(10.00), "AccountID4", "USD"),
                new Transaction("_4txn4", BigDecimal.valueOf(5.00), "AccountID4", "USD")
        ), AccountType.DEPOSIT);

        return Arrays.asList(acc1, acc2, acc3, acc4);
    }

    static class AccountTypeComparator implements Comparator<AccountType> {

        private Map<AccountType, Integer> pos;

        static AccountTypeComparator forOrder(AccountType ... expectedOrder) {
            return new AccountTypeComparator(expectedOrder);
        }

        private AccountTypeComparator(AccountType... expectedOrder) {
            this.pos = new HashMap<>();
            for (int i = 0; i < expectedOrder.length; i++) {
                pos.put(expectedOrder[i], i);
            }
        }

        @Override
        public int compare(AccountType o1, AccountType o2) {
            Integer p1 = pos.getOrDefault(o1, -1);
            Integer p2 = pos.getOrDefault(o2, -1);
            return p1.compareTo(p2);
        }

    }

}
