package lv.nixx.poc.spring5poc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lv.nixx.poc.spring5poc.domain.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MockTransactionRepository implements TransactionRepository {

	private final Map<Integer, Transaction> txns = new HashMap<>();

	public MockTransactionRepository() {
		this.txns.put(1, new Transaction("txnId1", BigDecimal.valueOf(10.78), "NEW"));
		this.txns.put(2, new Transaction("txnId1", BigDecimal.valueOf(1.40), "ACCEPTED"));
	}

	@Override
	public Mono<Transaction> getTransactionById(int id) {
		return Mono.justOrEmpty(this.txns.get(id));
	}

	@Override
	public Flux<Transaction> getAllTransactions() {
		return Flux.fromIterable(this.txns.values());
	}

	@Override
	public Mono<Void> saveTransaction(Mono<Transaction> txnMono) {
		return txnMono.doOnNext(txn -> {
			int id = txns.size() + 1;
			txns.put(id, txn);
			System.out.format("Saved %s with id %d%n", txn, id);
		}).thenEmpty(Mono.empty());
	}
}
