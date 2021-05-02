package lv.nixx.poc.freemarkerpoc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lv.nixx.poc.freemarkerpoc.model.DataModel;

import lv.nixx.poc.freemarkerpoc.model.Customer;
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
class HtmlCreationUsingFreemarkerSample {

    @Autowired
    private Configuration configuration;

    @Test
    void sample() throws Exception {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        DataModel dm = new DataModel()
                .setTotalAmount(BigDecimal.valueOf(777_000))
                .setCustomers(List.of(
                        new Customer()
                                .setId(101)
                                .setName("John")
                                .setSurname("Rambo")
                                .setDateOfBirth(df.parse("16/12/1960"))
                                .setType("SIMPLE"),
                        new Customer()
                                .setId(102)
                                .setName("John")
                                .setSurname("Travolta")
                                .setDateOfBirth(df.parse("01/01/1950"))
                                .setType("SIMPLE"),
                        new Customer()
                                .setId(103)
                                .setName("Nicolas")
                                .setSurname("Cage")
                                .setDateOfBirth(df.parse("01/12/1955"))
                                .setType("VIP"),
                        new Customer()
                                .setId(104)
                                .setName("Madonna")
                                .setSurname("Chikone")
                                .setDateOfBirth(df.parse("01/12/1965"))
                                .setType("VIP")
                        )
                );

        final Template template = configuration.getTemplate("email-template.ftl");
        final String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, dm);

        assertNotNull(s);

        Files.writeString(Paths.get("./sample.html"), s, StandardCharsets.UTF_8);

    }

}
