package lv.nixx.poc.spring5poc;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lv.nixx.poc.spring5poc.domain.Transaction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class TransactionHandler {

	private final TransactionRepository repository;

	public TransactionHandler(TransactionRepository repository) {
		this.repository = repository;
	}

	public Mono<ServerResponse> getTransaction(ServerRequest request) {
		int personId = Integer.valueOf(request.pathVariable("id"));
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<Transaction> personMono = this.repository.getTransactionById(personId);
		return personMono
				.flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(person)))
				.switchIfEmpty(notFound);
	}


	public Mono<ServerResponse> createTransaction(ServerRequest request) {
		Mono<Transaction> person = request.bodyToMono(Transaction.class);
		return ServerResponse.ok().build(this.repository.saveTransaction(person));
	}

	public Mono<ServerResponse> getTransactionList(ServerRequest request) {
		Flux<Transaction> people = this.repository.getAllTransactions();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, Transaction.class);
	}

}
