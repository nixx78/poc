package lv.nixx.poc.spring5poc;

import reactor.ipc.netty.http.server.HttpServer;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class TransactionRestServer {

	public static final String HOST = "localhost";
	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		TransactionRestServer server = new TransactionRestServer();
		server.startReactorServer();

		System.out.println("Press ENTER to exit.");
		System.in.read();
	}

	public RouterFunction<ServerResponse> routingFunction() {
		TransactionRepository repository = new MockTransactionRepository();
		TransactionHandler handler = new TransactionHandler(repository);

		return nest(path("/transaction"),
				nest(accept(APPLICATION_JSON),
						route(GET("/{id}"), handler::getTransaction)
						.andRoute(method(HttpMethod.GET), handler::getTransactionList))
						.andRoute(POST("/").and(contentType(APPLICATION_JSON)),handler::createTransaction));
	}

	public void startReactorServer() throws InterruptedException {

		RouterFunction<ServerResponse> route = routingFunction();
		HttpHandler httpHandler = toHttpHandler(route);

		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create(HOST, PORT);
		server.newHandler(adapter).block();
	}

}
