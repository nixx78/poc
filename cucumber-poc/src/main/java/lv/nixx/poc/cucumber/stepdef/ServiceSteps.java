package lv.nixx.poc.cucumber.stepdef;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.omg.Messaging.SyncScopeHelper;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.table.TableConverter;
import gherkin.formatter.model.DataTableRow;
import lv.nixx.poc.cucumber.service.Service;

public class ServiceSteps {

	Service service;

	@Given("^Service is avaliable$")
	public void service_is_avaliable() throws Throwable {
		service = new Service();
	}

	@When("User (.*) is login succesfully")
	public void userIsLoggedIn(String user) {
	}

	@Then("^Calculate: (\\d+) \\+ (\\d+) expected (\\d+)$")
	public void calculate(int a, int b, int r) {
		assertEquals(r, service.calculate(a, b));
	}
	
	@Given("^Users with passwords exists:$")
	public void usersWithPasswordsExists(DataTable dataTable) {
		final TableConverter tableConverter = dataTable.getTableConverter();
		final Map<String, String> map = tableConverter.toMap(dataTable, String.class, String.class);
		map.entrySet().stream().forEach(System.out::println);
	}


}
