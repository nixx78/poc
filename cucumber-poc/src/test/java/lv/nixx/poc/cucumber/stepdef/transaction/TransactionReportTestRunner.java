package lv.nixx.poc.cucumber.stepdef.transaction;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features/transaction", 
		plugin = { "pretty", "html:target/cucumber" }, 
		glue = "lv.nixx.poc.cucumber.stepdef.transaction")
public class TransactionReportTestRunner {
}
