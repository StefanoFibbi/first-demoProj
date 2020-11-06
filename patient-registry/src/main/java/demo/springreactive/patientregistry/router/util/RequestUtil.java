package demo.springreactive.patientregistry.router.util;

import org.springframework.web.reactive.function.server.ServerRequest;

public final class RequestUtil {

	private RequestUtil() {
	}

	public static String id(ServerRequest request) {
		return request.pathVariable("id");
	}

}
