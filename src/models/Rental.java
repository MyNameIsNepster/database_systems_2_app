package models;

import java.time.LocalDate;

public class Rental {
    private int id;
    private String name;
    private String city;
    private int employees;
    private LocalDate dateFounded;

    public Rental(int id, String name, String city, int employees, LocalDate dateFounded) {
	super();
	this.id = id;
	this.name = name;
	this.city = city;
	this.employees = employees;
	this.dateFounded = dateFounded;
    }
    
    public Rental(String name, String city, int employees, LocalDate dateFounded) {
	super();
	this.name = name;
	this.city = city;
	this.employees = employees;
	this.dateFounded = dateFounded;
    }

    public String toString() {
	return "Rental [id=" + id + ", name=" + name + ", city=" + city + ", employees=" + employees + ", dateFounded="
		+ dateFounded + "]";
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public int getEmployees() {
	return employees;
    }

    public void setEmployees(int employees) {
	this.employees = employees;
    }

    public LocalDate getDateFounded() {
	return dateFounded;
    }

    public void setDateFounded(LocalDate dateFounded) {
	this.dateFounded = dateFounded;
    }

}
