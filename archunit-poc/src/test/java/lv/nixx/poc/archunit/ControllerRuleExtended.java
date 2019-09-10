package lv.nixx.poc.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(
        packages = "lv.nixx.poc.archunit.controller",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ControllerRuleExtended {

    @ArchTest
    public static void controllerAnnotationRule(JavaClasses classes) {
        ArchRule rule = classes().should()
                .resideInAPackage("..controller..").andShould().beAnnotatedWith(RestController.class);

        rule.check(classes);
    }

    @ArchTest
    public static void controllerMethodsRule(JavaClasses classes) {
        ArchRule rule = methods().that()
                .areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().notBeAnnotatedWith(RequestMapping.class);

        rule.check(classes);
    }

    @ArchTest
    public void packageDependencyCheck(JavaClasses classes) {
        ArchRule rule = noClasses().that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..dao..", "..hazelcast..");

        rule.check(classes);
    }

}