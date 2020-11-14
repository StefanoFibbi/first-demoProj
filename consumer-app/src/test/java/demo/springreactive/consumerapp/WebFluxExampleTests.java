package demo.springreactive.consumerapp;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class WebFluxExampleTests {

	@Test
	void example1_testMonoSubscribe() {
		Mono
				.just("Hello")
				.doOnNext(System.out::println)
				.subscribe();
	}

	@Test
	void example2_testMonoBlock() {
		Mono
				.just("Hello")
				.doOnNext(System.out::println)
				.block();
	}

	@Test
	void example3_testFlux() {
		Flux
				.just(1, 2, 3, 4)
				.doOnNext(System.out::println)
				.blockLast();
	}

	@Test
	void example4_initFluxFromList() {
		List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
		Flux
				.fromIterable(list)
				.doOnNext(System.out::println)
				.blockLast();
	}

	@Test
	void example5_combineMonoAndFlux() {
		List<String> names = Lists.newArrayList("John", "Greg");
		Mono
				.just("Hello")
				.flatMapMany(prefix ->
						Flux
								.fromIterable(names)
								.map(name -> prefix + " " + name)
				)
				.doOnNext(System.out::println)
				.blockLast();
	}

	@Test
	void example6_displayLogsWithNoBackpressure() {
		Flux
				.range(1, 10)
				.log()
				.doOnNext(System.out::println)
				.subscribe();
	}

	@Test
	void example7_displayLogsWithBackpressure() {
		Flux
				.range(1, 10)
				.log()
				.doOnNext(System.out::println)
				.subscribe(new Subscriber<Integer>() {

					final int elementsPerRequest = 3;
					Subscription subscription;
					int receivedElements = 0;

					@Override
					public void onSubscribe(Subscription s) {
						this.subscription = s;
						this.subscription.request(this.elementsPerRequest);
					}

					@Override
					public void onNext(Integer integer) {
						this.receivedElements++;

						if ((this.receivedElements % this.elementsPerRequest) == 0) {
							this.receivedElements = 0;
							this.subscription.request(this.elementsPerRequest);
						}
					}

					@Override
					public void onError(Throwable t) {

					}

					@Override
					public void onComplete() {
						this.subscription.cancel();
					}
				});
	}
}
