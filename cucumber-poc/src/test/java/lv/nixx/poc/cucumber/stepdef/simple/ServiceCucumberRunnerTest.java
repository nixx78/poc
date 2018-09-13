package lv.nixx.poc.cucumber.stepdef.simple;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features/simple", 
		plugin = { "pretty", "html:target/cucumber" }, 
		glue = "lv.nixx.poc.cucumber.stepdef.simple")
public class ServiceCucumberRunnerTest {
}
