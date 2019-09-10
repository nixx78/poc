package lv.nixx.poc.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(
        packages = "lv.nixx.poc.archunit.service",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class GeneralRules {

    @ArchTest
    public static void entityManagerUsageRules(JavaClasses classes) {

        ArchRule rule = classes().that().resideInAPackage("javax.persistence")
                .should().onlyBeAccessed().byClassesThat().resideInAPackage("..dao..");

        rule.check(classes);
    }

}