package lv.nixx.poc.camel.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="persons")
public class PersonList {
	
	private List<Person> persons;

	@XmlElement(name="person")
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	@Override
	public String toString() {
		return "PersonList [persons=" + persons + "]";
	}

}
