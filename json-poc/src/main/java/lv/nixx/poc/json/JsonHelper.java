package lv.nixx.poc.json;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonHelper {
	
	
	public static Gson gson(){

		final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
   		  @Override
		  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
  		    try {
				return json == null ? null : df.parse(json.getAsString());
			} catch (ParseException e) {
				throw new JsonParseException(e);
			}
		  }
		};
		
		JsonSerializer<Date> ser = new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date date, Type type, JsonSerializationContext arg2) {
				return new JsonPrimitive(df.format(date));
			}
		};
		
		
		JsonSerializer<BigDecimal> bigDecimalSer = new JsonSerializer<BigDecimal>() {
			@Override
			public JsonElement serialize(BigDecimal bd, Type type, JsonSerializationContext arg2) {
				return new JsonPrimitive(bd.setScale(2, RoundingMode.HALF_UP));
			}
		};
		
		JsonDeserializer<BigDecimal> bigDecimalDeser = new JsonDeserializer<BigDecimal>() {
	   		  @Override
			  public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	  		    try {
	  		    	final DecimalFormat df = new DecimalFormat();
	  		    	df.setParseBigDecimal(true);
					BigDecimal bigDecimal = (BigDecimal) (json == null ? null : df.parse(json.getAsString()));
					return bigDecimal.setScale(2, RoundingMode.HALF_UP);
				} catch (ParseException e) {
					throw new JsonParseException(e);
				}
			  }
			};
		
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, deser);
		gsonBuilder.registerTypeAdapter(Date.class, ser);
		gsonBuilder.registerTypeAdapter(BigDecimal.class, bigDecimalSer);
		gsonBuilder.registerTypeAdapter(BigDecimal.class, bigDecimalDeser);
		return gsonBuilder.create();
	}

}
