package lv.nixx.poc.sandbox.collection.txn;

import lv.nixx.poc.domain.Transaction;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

public class TxnLambdaModification {

    @Test
    public void functionCall() {

        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        TxnProcessor myProcessor = new TxnProcessor();
        // ссылка на метод, у конкретного экземпляра класса
        txns.forEach(myProcessor::simpleMethod);
        // вызов статического метода с параметрами
        txns.forEach(t -> TxnProcessor.staticMethod(t.getAmount(), t.getCurrency()));

        // Moжно вызывать и вот так, поскольку, MyProcessor implements Consumer
        MyProcessor mp = new MyProcessor();
        txns.forEach(mp);
    }

    @Test(expected = IllegalStateException.class)
    public void transformCollectionToMapDuplicateKey() {

        Collection<Transaction> txnSet = new HashSet<>();
        txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
        txnSet.add(new Transaction("id1", BigDecimal.valueOf(99.10), "ACC1", "USD"));
        txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
        txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
        txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));

        txnSet.stream().collect(Collectors.toMap(Transaction::getId, Function.identity()));
    }


    @Test
    public void createAccountHolder() {
        Collection<Transaction> txnSet = new HashSet<>();
        txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
        txnSet.add(new Transaction("id1", BigDecimal.valueOf(99.10), "ACC1", "USD"));
        txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
        txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
        txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));

        AccountHolder ah = new AccountHolder();
        txnSet.forEach(ah::process);

        ah.accounts.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    @Test
    public void getUniqueAccounts() {
        Collection<Transaction> txnSet = new HashSet<>();
        txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
        txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
        txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
        txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));
        txnSet.add(new Transaction("id5", BigDecimal.valueOf(40.14), "ACC5", "EUR"));

        final Set<String> accounts = txnSet.stream()
                .map(Transaction::getAccount)
                .collect(Collectors.toSet());

        assertEquals(4, accounts.size());
        accounts.forEach(System.out::println);
    }

    @Test
    public void stringWithUniqueCurrencies() {
        Collection<Transaction> txnSet = new HashSet<>();
        txnSet.add(new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"));
        txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"));
        txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"));
        txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"));
        txnSet.add(new Transaction("id5", BigDecimal.valueOf(40.14), "ACC5", "EUR"));

        String c = txnSet.stream()
                .map(Transaction::getCurrency)
                .distinct()
                .collect(Collectors.joining(", "));

        assertEquals("EUR, USD", c);
    }

    @Test
    public void transformCollectionToMapIgnoreDuplicates() throws ParseException {

        final String id1 = "id1";
        final Transaction id1LastVersion = new Transaction(id1, BigDecimal.valueOf(99.10), "ACC1.Last", "GBP", getDate("10.09.2016"));

        Set<Transaction> txnSet = new HashSet<>();
        txnSet.add(new Transaction(id1, BigDecimal.valueOf(10.10), "ACC1", "GBP", getDate("01.09.2016")));
        txnSet.add(new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.09.2016")));
        txnSet.add(new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR", getDate("01.09.2016")));
        txnSet.add(new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR", getDate("01.09.2016")));
        txnSet.add(id1LastVersion);


        @SuppressWarnings("unused")
        BinaryOperator<Transaction> mergeFunction = (oldValue, newValue) -> oldValue.getLastUpdateDate().compareTo(newValue.getLastUpdateDate()) <= 0 ? newValue : oldValue;
        // mergeFunction и mergeFunction1 делают одно и тоже, просто разная форма записи
        BinaryOperator<Transaction> mergeFunction1 = (oldValue, newValue) -> oldValue.getLastUpdateDate().compareTo(newValue.getLastUpdateDate()) <= 0 ? newValue : oldValue;

        // Если не указать mergeFunction, то данный запрос будет "падать", так как коллекция содержит повторяющиеся элементы
        final Map<String, Transaction> collect = txnSet.stream()
                .collect(Collectors.toMap(Transaction::getId, Function.identity(), mergeFunction1));

        collect.values().forEach(System.out::println);
        assertEquals(4, collect.size());

        assertEquals(id1LastVersion, collect.get(id1));
    }


    @Test
    public void transformCollectionToIdList() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        List<String> array = txns.stream()
                .filter(t -> t.getCurrency().equalsIgnoreCase("USD"))
                .map(Transaction::getId).toList();

        assertEquals(2, array.size());
    }

    @Test
    public void groupByAccountCurrencies() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id31", BigDecimal.valueOf(30.14), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
                new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        // Счет, список валют
        Map<String, Set<String>> c =
                txns.stream()
                        .collect(groupingBy(
                                Transaction::getAccount,
                                Collectors.mapping(Transaction::getCurrency, Collectors.toSet()))
                        );

        c.entrySet().forEach(System.out::println);
        assertEquals(3, c.size());

        System.out.println("--------------------");

        Map<String, List<Transaction>> transactionListByAccount = txns.stream()
                .collect(groupingBy(Transaction::getAccount));

        transactionListByAccount.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(v -> System.out.println("\t\t" + v));
        });

        assertEquals(3, transactionListByAccount.size());

    }

    @Test
    public void groupByAccountAmountList() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
                new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        Map<String, List<Amount>> c =
                txns.stream()
                        .collect(groupingBy(Transaction::getAccount,
                                        Collectors.mapping(Amount::new, Collectors.toList())
                                )
                        );

        c.entrySet().forEach(System.out::println);

        assertEquals(3, c.size());
    }

    @Test
    public void groupByCurrencyAccountStatistic() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
                new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
                new Transaction("id6", BigDecimal.valueOf(40.14), "ACC4", "RUR")

        );

        Map<String, Map<String, Long>> m1 = txns.stream()
                .collect(groupingBy(Transaction::getCurrency,
                                groupingBy(Transaction::getAccount, Collectors.counting())
                        )
                );

        m1.entrySet().forEach(System.out::println);

        assertEquals(3, m1.size());

        Map<String, Map<String, List<Transaction>>> m2 = txns.stream()
                .collect(groupingBy(Transaction::getCurrency,
                                groupingBy(Transaction::getAccount, Collectors.toList())
                        )
                );

        /*
         EUR ->
                ACC3(id5)
                ACC2(id3, id4)
         */
        assertEquals(3, m2.size());

        Map<String, Map<String, Transaction>> m3 = txns.stream()
                .collect(groupingBy(Transaction::getCurrency,
                                toMap(Transaction::getAccount, Function.identity(), (t1, t2) -> t2)
                        )
                );

        assertEquals(3, m2.size());
    }

    @Test
    public void mapAndGroup() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(20.00), "ACC2", "EUR"),
                new Transaction("id5", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        Collection<Amount> c =
                txns.stream()
                        .map(Amount::new).collect(Collectors.toSet());

        c.forEach(System.out::println);

        assertEquals(5, c.size());
    }

    @Test
    public void totalAmountByCurrency() {
        Set<Amount> set = new HashSet<>();
        set.add(new Amount("EUR", BigDecimal.valueOf(30.13)));
        set.add(new Amount("EUR", BigDecimal.valueOf(20.0)));
        set.add(new Amount("USD", BigDecimal.valueOf(20.12)));
        set.add(new Amount("USD", BigDecimal.valueOf(50)));
        set.add(new Amount("GBP", BigDecimal.valueOf(150)));

        Map<String, Optional<Amount>> r = set.stream()
                .collect(
                        groupingBy(Amount::getCurrency,
                                Collectors.reducing(Amount::increase))
                );

        r.entrySet().forEach(System.out::println);

        assertEquals(3, r.size());
    }

    @Test
    public void txnCountByCurrency() {
        Set<Amount> set = new HashSet<>();
        set.add(new Amount("EUR", BigDecimal.valueOf(30.13)));
        set.add(new Amount("EUR", BigDecimal.valueOf(20.0)));
        set.add(new Amount("USD", BigDecimal.valueOf(20.12)));
        set.add(new Amount("USD", BigDecimal.valueOf(50)));
        set.add(new Amount("GBP", BigDecimal.valueOf(150)));

        Map<String, Long> r = set.stream()
                .collect(
                        groupingBy(Amount::getCurrency,
                                Collectors.counting())
                );

        r.entrySet().forEach(System.out::println);

        assertEquals(3, r.size());
    }


    @Test
    public void partitioningBy() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        final Map<Boolean, List<Transaction>> c = txns.stream()
                .collect(Collectors.partitioningBy(t -> t.getCurrency().equals("USD")));

        c.forEach((b, t) -> System.out.println(b + ":" + t));
    }

    @Test
    public void groupByAccountWithCondition() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
                new Transaction("id5", BigDecimal.valueOf(50.14), "ACC3", "EUR"),
                new Transaction("id6", BigDecimal.valueOf(60.14), "ACC3", "USD")

        );

        Map<String, List<Transaction>> collect =
                txns.stream()
                        .collect(
                                groupingBy(Transaction::getAccount,
                                        Collectors.filtering(t -> t.getCurrency().equalsIgnoreCase("EUR"), Collectors.toList())
                                )
                        );

        collect.entrySet().forEach(System.out::println);
    }

    @Test
    public void txnCreateFromString() throws Exception {
        String txnString =
                "id1, 10.30, ACC1, USD\n" +
                        "id2, 20.40, ACC2, USD\n" +
                        "id3, 5.00,  ACC3, EUR\n" +
                        "id4, 20.00, ACC3, EUR\n" +
                        "id5, 15.00, ACC4, EUR\n";

        try (BufferedReader br = new BufferedReader(new StringReader(txnString))) {
            final List<Transaction> txnList = br.lines()
                    .map(t -> t.split(","))
                    .filter(t -> t.length == 4)
                    .map(this::removeSpaces)
                    .map(t -> new Transaction(t[0], parse(t[1]), t[2], t[3]))
                    .collect(Collectors.toList());

            txnList.forEach(System.out::println);
        }
    }

    @Test
    public void sortTxnBySeveralParams() {
        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(1.00), "ACC3", "EUR")
        );

        final Comparator<Transaction> c =
                Comparator.comparing(Transaction::getAccount).reversed()
                        .thenComparing(Transaction::getCurrency)
                        .thenComparing(Transaction::getAmount);

        List<Transaction> sorted =
                txns.stream()
                        .sorted(c)
                        .toList();

        sorted.forEach(System.out::println);
    }

    @Test
    public void mapWithIncrementTest() {

        List<Transaction> txns = Arrays.asList(
                new Transaction("id5", BigDecimal.valueOf(1.00), "ACC3", "EUR"),
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        final AtomicInteger i = new AtomicInteger(0);
        Map<Integer, Transaction> map = txns.stream()
                .collect(Collectors.toMap(t -> i.incrementAndGet(), Function.identity()));

        System.out.println(map);
    }

    @Test
    public void top3TransactionTest() {

        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(1.00), "ACC3", "EUR")
        );

        List<Transaction> top3Txns =
                txns.stream()
                        .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                        .limit(3)
                        .collect(Collectors.toList());

        top3Txns.forEach(System.out::println);

        assertEquals(3, top3Txns.size());
    }

    @Test
    public void topTransactionByAccountTest() {

        List<Transaction> txns = Arrays.asList(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR"),
                new Transaction("id4", BigDecimal.valueOf(1.00), "ACC3", "EUR")
        );

        Map<String, Transaction> maxBy = txns.stream()
                .collect(
                        Collectors.toMap(Transaction::getAccount, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Transaction::getAmount)))
                );

        assertEquals(3, maxBy.size());
    }

    @Test
    public void getTransactionsByPeriod() throws ParseException {

        List<Transaction> txns = List.of(
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD", getDate("21.04.2020")),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD", getDate("01.05.2020")),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR", getDate("02.05.2020")),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR", getDate("03.05.2020")),
                new Transaction("id5", BigDecimal.valueOf(1.00), "ACC3", "EUR", getDate("01.06.2020"))
        );

        DateFilter df = new DateFilter(getDate("01.05.2020"), getDate("01.06.2020"));

        final List<Transaction> datesInRange = txns.stream()
                .filter(t -> df.isInRange(t.getLastUpdateDate()))
                .collect(Collectors.toList());

        System.out.println(datesInRange);

        assertEquals(4, datesInRange.size());

    }

    static class DateFilter {
        final Date dateFrom;
        final Date dateTo;

        DateFilter(Date dateFrom, Date dateTo) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        boolean isInRange(Date date) {
            return (date.compareTo(dateFrom) >= 0 && date.compareTo(dateTo) <= 0);
//          return (date.equals(dateFrom) || date.after(dateFrom)) && (date.equals(dateTo) || date.before(dateTo));
        }

    }

    @Test
    public void peekTest() {

        List<Transaction> txns = Arrays.asList(
                new Transaction("id5", BigDecimal.valueOf(1.00), "ACC3", "EUR"),
                new Transaction("id1", BigDecimal.valueOf(10.10), "ACC1", "USD"),
                new Transaction("id3", BigDecimal.valueOf(30.13), "ACC2", "EUR"),
                new Transaction("id2", BigDecimal.valueOf(20.12), "ACC2", "USD"),
                new Transaction("id4", BigDecimal.valueOf(40.14), "ACC3", "EUR")
        );

        List<String> lst = txns.stream().map(Transaction::getId).peek(t -> System.out.println(t + "V")).collect(Collectors.toList());
        System.out.println("========================");

        System.out.println(lst);
    }

    private String[] removeSpaces(String[] source) {
        for (int i = 0; i < source.length; i++) {
            source[i] = source[i].trim();
        }
        return source;
    }

    private BigDecimal parse(String amount) {
        DecimalFormat df = new DecimalFormat();
        df.setParseBigDecimal(true);
        try {
            return (BigDecimal) df.parse(amount.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    class AccountHolder {

        Map<String, LocalAccount> accounts = new HashMap<>();

        public void process(Transaction txn) {
            String accId = txn.getAccount();
            LocalAccount account = null;
            if (accounts.containsKey(accId)) {
                account = accounts.get(accId);
            } else {
                account = new LocalAccount(accId);
            }
            account.increase(txn.getCurrency(), txn.getAmount());
            accounts.put(accId, account);
        }

    }

    static class LocalAccount {
        private final String id;
        Map<String, Amount> amounts = new HashMap<>();

        public LocalAccount(String accId) {
            this.id = accId;
        }

        public LocalAccount increase(Transaction txn) {
            final String currency = txn.getCurrency();
            final BigDecimal amount = txn.getAmount();
            if (amounts.containsKey(currency)) {
                amounts.get(currency).increase(amount);
            } else {
                amounts.put(currency, new Amount(currency, amount));
            }
            return this;
        }


        public void increase(String currency, BigDecimal amount) {
            if (amounts.containsKey(currency)) {
                amounts.get(currency).increase(amount);
            } else {
                amounts.put(currency, new Amount(currency, amount));
            }
        }

        @Override
        public String toString() {
            return "Account [id=" + id + ", amounts=" + amounts + "]";
        }
    }

    static class Amount {
        private final String currency;
        private BigDecimal totalAmount;

        Amount(Transaction txn) {
            this.currency = txn.getCurrency();
            this.totalAmount = txn.getAmount();
        }

        Amount(String currency, BigDecimal amount) {
            this.currency = currency;
            this.totalAmount = amount;
        }

        public void increase(BigDecimal amount) {
            this.totalAmount = totalAmount.add(amount);
        }

        public Amount increase(Amount totalAmount) {
            increase(totalAmount.getTotalAmount());
            return this;
        }

        public String getCurrency() {
            return currency;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        @Override
        public String toString() {
            return "TotalAmount [currency=" + currency + ", totalAmount=" + totalAmount + "]";
        }
    }

    static class TxnProcessor {
        public void simpleMethod(Transaction t) {
            System.out.println(t);
        }

        public static void staticMethod(BigDecimal amount, String currency) {
            System.out.println(amount + ":" + currency);
        }
    }

    static class MyProcessor implements Consumer<Transaction> {

        @Override
        public void accept(Transaction t) {
            System.out.println("#" + t);
        }

    }

    private Date getDate(String date) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }


}
