package lv.nixx.poc.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(
        packages = "lv.nixx.poc.controller",
        importOptions =  ImportOption.DoNotIncludeTests.class
)
public class RunWithSample {

    @ArchTest
    public static void controllerAnnotationRule(JavaClasses classes) {

        ArchRule rule = ArchRuleDefinition.classes().should()
                .resideInAPackage("..controller..").andShould().beAnnotatedWith(Controller.class);

        rule.check(classes);
    }

    @ArchTest
    public static void controllerMethodsRule(JavaClasses classes) {
        ArchRule rule = ArchRuleDefinition.methods().that()
                .areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().notBeAnnotatedWith(RequestMapping.class)
                .andShould().haveRawReturnType(String.class);

        rule.check(classes);
    }

}