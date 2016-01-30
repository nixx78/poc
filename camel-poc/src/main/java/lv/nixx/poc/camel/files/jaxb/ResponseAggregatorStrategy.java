package lv.nixx.poc.camel.files.jaxb;

import java.util.ArrayList;
import java.util.List;

import lv.nixx.poc.camel.model.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ResponseAggregatorStrategy implements AggregationStrategy {
	
	@SuppressWarnings("unchecked")
	@Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Message newIn = newExchange.getIn();
        
        Response newBody = newIn.getBody(Response.class);
        List<Response> list = null;
        if (oldExchange == null) {
                list = new ArrayList<>();
                list.add(newBody);
                newIn.setBody(list);
                return newExchange;
        } else {
                Message in = oldExchange.getIn();
                list = in.getBody(ArrayList.class);
                list.add(newBody);
                return oldExchange;
        }
    }
}