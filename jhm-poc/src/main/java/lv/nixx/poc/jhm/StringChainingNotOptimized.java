package lv.nixx.poc.jhm;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 10, time = 1, batchSize = 1000)
@Measurement(iterations = 10, time = 1, batchSize = 1000)
public class StringChainingNotOptimized {

	private String a1 = "111111111111111111111111";
	private String a2 = "222222222222222222222222";
	private String a3 = "333333333333333333333333";

	@Benchmark
	public String stringBuilderChaining() {
		return new StringBuilder().append(a1).append(a2).append(a3).toString();
	}
	
	@Benchmark
	public String stringConcat() {
		return a1 + a2 + a3;
	}


	@Benchmark
	public String stringBuilderNoChaining() {
		StringBuilder sb = new StringBuilder();
		sb.append(a1);
		sb.append(a2);
		sb.append(a3);
		return sb.toString();
	}

}
