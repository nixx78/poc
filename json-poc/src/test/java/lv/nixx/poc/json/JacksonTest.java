package lv.nixx.poc.json;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import lv.nixx.poc.domain.*;

public class JacksonTest {
	
	private ObjectMapperService objectMapper = new ObjectMapperService();
		
	@Test
	public void objectToStringSample() throws Exception {
		Transaction p = new Transaction(10, 1, BigDecimal.valueOf(10.00), Currency.EUR);
		
		String jsonString = objectMapper.writeValueAsString(p);
		System.out.println(jsonString);
		
	}
	
	@Test
	public void stringToObjectSample() throws Exception {
		String json = "{\r\n" + 
				"  \"id\" : 10,\r\n" + 
				"  \"amount\" : 10.0,\r\n" + 
				"  \"currency\" : \"EUR\"\r\n" + 
				"}";
		
		Transaction txn = objectMapper.readValue(json, Transaction.class);
		assertNotNull(txn);
		
		System.out.println(txn);
	}
	
	@Test
	public void requiredFieldTest() throws Exception {
		String json = "{\r\n" + 
				"  \"id\" : 10,\r\n" + 
				"  \"amount\" : 10.0\r\n" + 
				"}";
		
		Transaction txn = objectMapper.readValue(json, Transaction.class);
		assertNotNull(txn);
		
		System.out.println(txn);
	}

}
