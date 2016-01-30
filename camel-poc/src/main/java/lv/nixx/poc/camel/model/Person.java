package lv.nixx.poc.camel.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="person")
public class Person {
	
	private int id;
	private String name;
	private String surname;

	@XmlElement(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	@XmlElement(name="surname")
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + "]";
	}
	
}
