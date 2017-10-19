package lv.nixx.poc.spring5poc;

import lv.nixx.poc.spring5poc.domain.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
	Mono<Transaction> getTransactionById(int id);
	Flux<Transaction> getAllTransactions();
	Mono<Void> saveTransaction(Mono<Transaction> person);
}
