package lv.nixx.poc.sandbox.collection.txn;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class BatchExecutorPOC {
	
	@Test
	public void testBatchExecutor() {
		
		BatchExecutor<Integer, String> batchExecutor = new BatchExecutor<>();
		// Функция, куда будует передан результат вычесления
		batchExecutor.setCallback(System.out::println);

		batchExecutor.add(t-> {return t+":"+ System.currentTimeMillis();}, 100);
		batchExecutor.add(t-> {return t+"#"+ System.currentTimeMillis();}, 200);
		batchExecutor.add(t-> {return t+"$"+ System.currentTimeMillis();}, 200);
		
		batchExecutor.executeAll();
	}

	
	class BatchExecutor <T, U> {
		
		Set<Container<T,U>> container = new HashSet<>();
		Consumer<U> callback;
		
		public void setCallback(Consumer<U> callback) {
			this.callback = callback;
		}
		
		public void add(Function<T, U> funct, T data) {
			container.add(new Container<T,U>(funct, data));
		}

		public void executeAll() {
			container.stream().forEach(t -> {
				callback.accept( t.execute() );
				}
			);
		}
		
	}
	
	class Container <T, U>{

		private Function<T, U> function;
		private T data;
		
		Container(Function<T, U> function, T data) {
			this.function = function;
			this.data = data;
		}

		public U execute() {
			return function.apply(data);
		}
	}
	
}
