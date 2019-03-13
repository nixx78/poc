package lv.nixx.poc.jhm;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@Fork(value=1, jvmArgsAppend = "-XX:-OptimizeStringConcat")
@Warmup(iterations = 10, time = 1, batchSize = 1000)
@Measurement(iterations = 10, time = 1, batchSize = 1000)
public class StringChainingOptimized {

	private String s1 = "111111111111111111111111";
	private String s2 = "222222222222222222222222";
	private String s3 = "333333333333333333333333";

	@Benchmark
	public String stringBuilderChaining() {
		return new StringBuilder().append(s1).append(s2).append(s3).toString();
	}
	
	@Benchmark
	public String stringConcat() {
		return s1 + s2 + s3;
	}

	@Benchmark
	public String stringBuilderNoChaining() {
		StringBuilder sb = new StringBuilder();
		sb.append(s1);
		sb.append(s2);
		sb.append(s3);
		return sb.toString();
	}

}
