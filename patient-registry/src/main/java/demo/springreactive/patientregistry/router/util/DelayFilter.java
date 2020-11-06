package demo.springreactive.patientregistry.router.util;

import java.time.Duration;

import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class DelayFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

	@Override
	public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
		Duration delay = request.queryParam("delay")
				.map(rawValue -> Duration.ofSeconds(Long.parseLong(rawValue)))
				.orElse(Duration.ZERO);
		return delay.isZero() ?
				next.handle(request) :
				Mono.delay(delay).flatMap(aLong -> next.handle(request));
	}
}
