package lv.nixx.poc.camel.files.jaxb;

import java.util.List;

import lv.nixx.poc.camel.model.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class ResponseCreator implements Processor {
	@Override
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		List<Response> responseList = message.getBody(List.class);
		final StringBuilder sb = new StringBuilder();
		for (Response r: responseList) {
			sb.append(r.toString()); sb.append(System.lineSeparator());
		}
		sb.append("Total:" + responseList.size());
		message.setBody(sb.toString());
	}
}