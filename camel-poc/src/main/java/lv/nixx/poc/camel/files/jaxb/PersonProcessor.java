package lv.nixx.poc.camel.files.jaxb;

import lv.nixx.poc.camel.model.Person;
import lv.nixx.poc.camel.model.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class PersonProcessor implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();

		String fileName = String.valueOf(in.getHeader("CamelFileName"));
		Person person = in.getBody(Person.class);
		Response resp = new Response();
		resp.setId(person.getId());
		resp.setSuccess(person.getId() == 101 ? false: true );
		
		in.setBody(resp, Response.class);
		in.setHeader("CamelFileName", "person." + fileName );
		in.setHeader("id", fileName);
	}
}