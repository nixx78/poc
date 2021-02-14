package lv.nixx.poc.sandbox.collection;

import org.junit.After;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@State(value = Scope.Benchmark)
public class NumberStatisticSample {

	
	private final Collection<String> input = Arrays.asList("", "11", "aa1", "2", "2x", "123", "3333", "3x", "x3x", "z3z2", "55678", "XXX");
	
	@After
	public void after() {
		System.out.println("===============");
	}

	@Test
	public void launchBenchmark() throws Exception {

		Options opt = new OptionsBuilder()
				// Specify which benchmarks to run.
				// You can be more specific if you'd like to run only one benchmark per test.
				.include(this.getClass().getName() + ".*")
				// Set the following options as needed
				.mode(Mode.AverageTime)
				.timeUnit(TimeUnit.MICROSECONDS)
				.warmupTime(TimeValue.seconds(1))
				.warmupIterations(2)
				.measurementTime(TimeValue.seconds(1))
				.measurementIterations(2).threads(1).forks(1)
				.shouldFailOnError(true)
				.shouldDoGC(true)
				// .jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
				// .addProfiler(WinPerfAsmProfiler.class)
				.build();

		new Runner(opt).run();
	}


	// Посчитать количество элементов с вхождением различных символов,
	// например, 1an, 222as, 213 = "1" -> 2, "2" -> 2, "3" -> 1

	@Test
	@Benchmark
	public void firstApproach() {

		final Collection<String> checkedChars = Arrays.asList("1", "2", "3");

		final Map<String, Integer> statisic = new HashMap<>();

		for (String t : input) {
			checkedChars.forEach(chr -> {
				if (t.contains(chr)) {
					statisic.merge(chr, 1, (t1, t2) -> t1 + t2);
				}
			});
		}
		// statisic.entrySet().forEach(System.out::println);
	}

	@Test
	@Benchmark
	public void secondApproach() {
		final Map<String, Long> statisic = Stream.of("1", "2", "3").collect(Collectors.toMap(c -> c, c -> input.stream().filter(t -> t.contains(c)).count()));
		//statisic.entrySet().forEach(System.out::println);
	}
	
	@Test
	@Benchmark
	public void thirdApproach() {
		
		final Map<String, Integer> statisic = new HashMap<>();

		Stream.of("1", "2", "3").forEach(t -> {
			for(String s: input) {
				if (s.contains(t)) {
					statisic.merge(t, 1, (t1, t2) -> t1 + t2);
				}
				
			}
		});
		//statisic.entrySet().forEach(System.out::println);
	}

}
