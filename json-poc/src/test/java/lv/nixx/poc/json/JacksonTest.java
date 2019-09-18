package lv.nixx.poc.json;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;
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
				"  \"amount\" : 10.0,\r\n" +
				"  \"currency\": null" +
				"}";
		
		Transaction txn = objectMapper.readValue(json, Transaction.class);
		assertNotNull(txn);
		
		System.out.println(txn);
	}

	@Test
	public void jsonNodeCompareSuccessTest() throws IOException {
		String expectedJson = "{\"id\":10,\"amount\":10.0}";
		String   actualJson = "{\"amount\":10.0,\"id\":10}";

		JsonNode expectedNode = objectMapper.readTree(expectedJson);
		JsonNode actualNode = objectMapper.readTree(actualJson);

		assertEquals(expectedNode, actualNode);
	}

	@Test
	public void jsonNodeCompareFailTest() throws IOException {
		String expectedJson = "{\"id\":10,\"amount\": 10.0}";
		String actualJson   = "{\"id\":10,\"amount\": 10.1}";

		JsonNode expectedNode = objectMapper.readTree(expectedJson);
		JsonNode actualNode = objectMapper.readTree(actualJson);

		assertNotEquals(expectedNode, actualNode);
	}

}
