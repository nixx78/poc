package lv.nixx.poc.annotation;

@FastProcessing
@ProcessorAnnotation
public class DrinkOrderProcessor implements OrderProcessor {

	@Override
	public void processOrder(Order order) {
	}

}
