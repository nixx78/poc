package lv.nixx.poc.spring5poc;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;

import lv.nixx.poc.spring5poc.domain.Transaction;

public class Client {

	private ExchangeFunction exchange = ExchangeFunctions.create(new ReactorClientHttpConnector());


	public static void main(String[] args) throws Exception {
		Client client = new Client();
		client.createPerson();
		client.printAllPeople();
	}

	public void printAllPeople() {
		URI uri = URI.create(String.format("http://%s:%d/transaction", TransactionRestServer.HOST, TransactionRestServer.PORT));
		ClientRequest request = ClientRequest.method(HttpMethod.GET, uri).build();

		Flux<Transaction> people = exchange.exchange(request)
				.flatMapMany(response -> response.bodyToFlux(Transaction.class));

		Mono<List<Transaction>> peopleList = people.collectList();
		System.out.println(peopleList.block());
	}

	public void createPerson() {
		URI uri = URI.create(String.format("http://%s:%d/transaction", TransactionRestServer.HOST, TransactionRestServer.PORT));
		Transaction jack = new Transaction("txnId1", BigDecimal.valueOf(19.88), "NEW");

		ClientRequest request = ClientRequest.method(HttpMethod.POST, uri)
				.body(BodyInserters.fromObject(jack)).build();

		Mono<ClientResponse> response = exchange.exchange(request);

		System.out.println(response.block().statusCode());
	}

}
