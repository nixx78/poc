package lv.nixx.poc.json;

import com.google.gson.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonHelper {

    private JsonHelper() {}

    public static Gson gson() {

        final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        JsonDeserializer<Date> deser = (json, typeOfT, context) -> {
            try {
                return json == null ? null : df.parse(json.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        };

        JsonSerializer<Date> ser = (date, type, arg2) -> new JsonPrimitive(df.format(date));


        JsonSerializer<BigDecimal> bigDecimalSer = (bd, type, arg2) -> new JsonPrimitive(bd.setScale(2, RoundingMode.HALF_UP));

        JsonDeserializer<BigDecimal> bigDecimalDeser = (json, typeOfT, context) -> {
            try {
                final DecimalFormat df1 = new DecimalFormat();
                df1.setParseBigDecimal(true);
                BigDecimal bigDecimal = (BigDecimal) (json == null ? null : df1.parse(json.getAsString()));
                return bigDecimal.setScale(2, RoundingMode.HALF_UP);
            } catch (ParseException e) {
                throw new JsonParseException(e);
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
