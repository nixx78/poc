package lv.nixx.poc.spring5poc;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import lv.nixx.poc.spring5poc.domain.Transaction;

public class TransactionHandlerTests {

	private WebTestClient testClient;

	@Before
	public void createTestClient() {
		TransactionRestServer server = new TransactionRestServer();
		this.testClient = WebTestClient.bindToRouterFunction(server.routingFunction())
				.configureClient()
				.baseUrl("http://localhost/transaction")
				.build();
	}

	@Test
	public void getPerson() throws Exception {
		this.testClient.get()
				.uri("/1")
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Transaction.class).hasSize(1).returnResult();
	}

	@Test
	public void getPersonNotFound() throws Exception {
		this.testClient.get()
				.uri("/42")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	public void getTransactionList() throws Exception {
		this.testClient.get()
				.uri("/")
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Transaction.class).hasSize(2).returnResult();
	}

	@Test
	public void createTransaction() throws Exception {
		Transaction jack = new Transaction("txn1", BigDecimal.valueOf(10), "NEW");

		this.testClient.post()
				.uri("/")
				.contentType(MediaType.APPLICATION_JSON)
				.syncBody(jack)
				.exchange()
				.expectStatus().isOk();

	}
}