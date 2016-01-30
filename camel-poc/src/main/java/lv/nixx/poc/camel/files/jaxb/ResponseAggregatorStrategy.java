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
        
        Response body = newIn.getBody(Response.class);
        List<Response> list = null;
        
        updateHeaders(oldExchange == null ? newIn: oldExchange.getIn(), body.isSuccess() );

        if (oldExchange == null) {
                list = new ArrayList<>();
                list.add(body);
                newIn.setBody(list);
                return newExchange;
        } else {
                Message in = oldExchange.getIn();
                list = in.getBody(ArrayList.class);
                list.add(body);
                return oldExchange;
        }
       
    }
	
	private void updateHeaders(Message message, boolean isSuccess){
		String header = isSuccess ? "success" : "fail";
		Integer val = message.getHeader(header, 0, Integer.class);
		message.setHeader(header, Integer.valueOf(val + 1));
	}
}