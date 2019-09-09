package lv.nixx.poc.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Test;
import org.springframework.stereotype.Controller;


public class ControllerRule {

    @Test
    public void controllerRules() {

        JavaClasses classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .importPackages("lv.nixx.poc.controller");


        ArchRule rule = ArchRuleDefinition.classes().should()
                .resideInAPackage("..controller..").andShould().beAnnotatedWith(Controller.class);


        rule.check(classes);
    }


}