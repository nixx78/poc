package lv.nixx.poc.junit5;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.TestExtensionContext;

public class BenchmarkExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
	
	private long launchTime;
	private Map<String, String> report = new HashMap<>();

	@Override
	public void beforeTestExecution(TestExtensionContext context) {
		launchTime = System.currentTimeMillis();
	}

	@Override
	public void afterTestExecution(TestExtensionContext context) {
		final Optional<Class<?>> testClass = context.getTestClass();
		final Optional<Method> testMethod = context.getTestMethod();
		
		String className = testClass.isPresent() ? testClass.get().getSimpleName() : "";
		String method = testMethod.isPresent() ? testMethod.get().getName() : "";
		
		long elapsedTime = System.currentTimeMillis() - launchTime;
		
		String message = className + ":" + method + "Test took :" + elapsedTime;
		report.put("Benchmark", message);
		
		context.publishReportEntry(report);
	}
	
}