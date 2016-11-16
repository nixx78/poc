package lv.nixx.poc.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class OrderProcessorFactory {
	
	int mediumProcessing;
	int fastProcessing;
	
	public OrderProcessor getProcessor(Class<? extends OrderProcessor> processorClass) {
		for (Annotation annotation : processorClass.getAnnotations()) {
			final Class<? extends Annotation> c = annotation.annotationType();
			if (c.equals(MediumProcessing.class)) {
				mediumProcessing++;
			} else if (c.equals(FastProcessing.class)) {
				fastProcessing++;
			}
		}
		
		return createInstane(processorClass);
	}

	private OrderProcessor createInstane(Class<? extends OrderProcessor> processorClass) {
		try {
			final Constructor<? extends OrderProcessor> constructor = processorClass.getConstructor(new Class<?>[0]);
			return constructor.newInstance(new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getMediumProcessing() {
		return mediumProcessing;
	}

	public int getFastProcessing() {
		return fastProcessing;
	}

	@Override
	public String toString() {
		return "OrderProcessorFactory [mediumProcessing=" + mediumProcessing + ", fastProcessing=" + fastProcessing + "]";
	}

}
