package lv.nixx.poc.freemarkerpoc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lv.nixx.poc.freemarkerpoc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@SpringBootTest
public class SimpleTemplateTest {

    @Autowired
    private Configuration configuration;

    @Test
    void test() throws IOException, TemplateException {
        final Template template = configuration.getTemplate("simple_template.ftl");

        Customer c = new Customer()
                .setName("Name.value")
                .setSurname("Surname.value");

        final String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, c);

        System.out.println(s);

    }

}
