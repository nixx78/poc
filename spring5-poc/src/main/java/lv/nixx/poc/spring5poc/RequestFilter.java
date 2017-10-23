package lv.nixx.poc.spring5poc;

import java.util.function.Consumer;

import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class RequestFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

	@Override
	public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
		System.out.println("Before handler invocation: '" + request.path() + "'");
		Mono<ServerResponse> response = next.handle(request);

		Consumer<ServerResponse> e = new Consumer<ServerResponse>() {
			@Override
			public void accept(ServerResponse resp) {
				System.out.println("HTTP Status code: " + resp.statusCode());
			}
		};
		response.subscribe(e);
		return response;
	}

}
