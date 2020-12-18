package lv.nixx.poc.freemarkerpoc;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lv.nixx.poc.freemarkerpoc.model.ConvertedJson;
import lv.nixx.poc.freemarkerpoc.model.Customer;
import lv.nixx.poc.freemarkerpoc.model.DataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MixedTemplateSample {

    @Autowired
    private Configuration configuration;

    private ObjectMapper om = new ObjectMapper()
            .configure(
                    JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true
            );

    @Test
    @DisplayName("Object to JSON with HTML in field sample")
    void sample() throws Exception {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        DataModel dm = new DataModel()
                .setTotalAmount(BigDecimal.valueOf(777_000))
                .setCustomers(List.of(
                        new Customer()
                                .setId(100)
                                .setName("John")
                                .setSurname("Rambo")
                                .setDateOfBirth(df.parse("16/12/1960")),
                        new Customer()
                                .setId(100)
                                .setName("John")
                                .setSurname("Travolta")
                                .setDateOfBirth(df.parse("01/01/1950")),
                        new Customer()
                                .setId(100)
                                .setName("Nicolas")
                                .setSurname("Cage")
                                .setDateOfBirth(df.parse("01/12/1955"))
                ));

        final Template template = configuration.getTemplate("mixed_template.ftl");
        final String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, dm);

        assertNotNull(s);

        Files.writeString(Paths.get("./mixed.json"), s, StandardCharsets.UTF_8);

        final ConvertedJson convertedJson = om.readValue(s, ConvertedJson.class);

        System.out.println(convertedJson);

        assertNotNull(convertedJson.getEditable());
        assertNotNull(convertedJson.getStaticData());

        final String s1 = om.writeValueAsString(convertedJson);
        System.out.println("Converted JSON");
        System.out.println(s1);
        System.out.println("====");


    }

}
