package lv.nixx.poc.sandbox.collection.txn;

import lv.nixx.poc.domain.Account;
import lv.nixx.poc.domain.AccountType;
import lv.nixx.poc.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AccountPlayground {

    @Test
    public void getAccountsWithMoreThan3Txn() {

        Stream<Account> accs = createAccountsWithTransactions().stream();

        final Set<String> collect = accs
                .filter(t -> t.getTxns() != null)
                .filter(a -> a.getTxns().stream().count() > 2)
                .map(a -> a.getId())
                .collect(Collectors.toSet());

        collect.forEach(System.out::println);
    }

    @Test
    public void doubleStatisticsAllTransactions() {

        final DoubleSummaryStatistics stat = createAccountsWithTransactions().stream()
                .filter(t -> t.getTxns() != null)
                .flatMap(a -> a.getTxns().stream())
                .collect(Collectors.summarizingDouble(t -> t.getAmount().doubleValue()));

        System.out.println(stat);
    }

    @Test
    public void processAllTransactions() {

        Collection<Account> accounts = createAccountsWithTransactions();

        // First approach
        Set<String> currencyList = accounts.stream()
                .filter(t -> t.getTxns() != null)
                .flatMap(t -> t.getTxns().stream())
                .map(Transaction::getCurrency)
                .collect(Collectors.toSet());

        assertThat(currencyList, containsInAnyOrder("EUR", "USD", "RUB"));

        // Second approach
        currencyList = accounts.stream()
                .filter(t -> t.getTxns() != null)
                .flatMap(t -> t.getTxns().stream())
                .filter(distinctByKey(Transaction::getCurrency))
                .map(Transaction::getCurrency)
                .collect(Collectors.toSet());

        assertThat(currencyList, containsInAnyOrder("EUR", "USD", "RUB"));

    }

    @Test
    public void getFirstElementSample() {

        List<Account> accounts = createAccountsWithTransactions();

        Map<String, Transaction> collect = accounts.stream()
                // For case, if getTxns() Collection can be casted to List
                .filter(t -> t.getTxns() != null)
                .collect(Collectors.toMap(Account::getId, t -> ((List<Transaction>) t.getTxns()).get(0)));


        // For case, if collection can't be casted to List
        //			.collect(Collectors.toMap(Account::getId, t-> new ArrayList<Transaction>(t.getTxns()).get(0)));

        assertEquals(3, collect.size());

    }

    @Test
    public void getTop3TransactionsForEachAccount() {

        Map<String, Collection<Transaction>> accountsWithTop3Txns = createAccountsWithTransactions()
                .stream()
                .filter(t -> t.getTxns() != null)
                .collect(Collectors.toMap(Account::getId, t -> t.getTxns().stream()
                        .sorted(Comparator.comparing(Transaction::getAmount))
                        .limit(3)
                        .collect(Collectors.toList()))
                );


        accountsWithTop3Txns.entrySet().forEach(System.out::println);
    }

    @Test
    public void getTransactionsWithLimitByAccount() {

        Map<String, Collection<Transaction>> accountsWithTransactions = createAccountsWithTransactions()
            .stream()
            .filter(t -> t.getTxns() != null)
            .collect(Collectors.toMap(Account::getId, t -> t.getTxns().stream()
                    .filter(x -> x.getAmount().compareTo(BigDecimal.TEN) > 0)
                    .collect(Collectors.toList()))
            );

        accountsWithTransactions.entrySet().forEach(System.out::println);

    }

    @Test
    public void createDistinctAccountList() {

        Set<String> uniqueAccounts = Stream.of(
                new Account("ACC3", AccountType.CARD),
                new Account("ACC3", AccountType.CURRENT),
                new Account("ACC3", AccountType.DEPOSIT),
                new Account("ACC1", AccountType.CARD),
                new Account("ACC2", AccountType.CARD),
                new Account("ACC3", AccountType.CARD),
                new Account("ACC1", AccountType.CARD)
        ).map(t -> new StringJoiner(".")
                .add(t.getId())
                .add(t.getType().name())
                .toString()
        ).collect(Collectors.toCollection(TreeSet::new));

        System.out.println(uniqueAccounts);
    }

    private List<Account> createAccountsWithTransactions() {
        Account acc1 = new Account("AccountID1", List.of(
                new Transaction("_1txn1", BigDecimal.valueOf(40.00), "AccountID1", "USD"),
                new Transaction("_1txn2", BigDecimal.valueOf(10.00), "AccountID1", "USD"),
                new Transaction("_1txn3", BigDecimal.valueOf(34.00), "AccountID1", "USD"),
                new Transaction("_1txn4", BigDecimal.valueOf(5.00), "AccountID1", "USD")
        ));


        Account acc2 = new Account("AccountID2", List.of(
                new Transaction("_2txn1", BigDecimal.valueOf(100.00), "AccountID2", "EUR"),
                new Transaction("_2txn2", BigDecimal.valueOf(20.00), "AccountID2", "EUR"),
                new Transaction("_2txn2", BigDecimal.valueOf(30.00), "AccountID2", "EUR"),
                new Transaction("_2txn2", BigDecimal.valueOf(10.00), "AccountID2", "EUR")

        ));


        Account acc3 = new Account("AccountID3", Collections.singletonList(
                new Transaction("_3txn1", BigDecimal.valueOf(10.00), "AccountID3", "RUB")
        ));

        Account acc4 = new Account("AccountID3", (List<Transaction>) null);
        return Arrays.asList(acc1, acc2, acc3, acc4);
    }


    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
