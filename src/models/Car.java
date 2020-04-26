package models;

import java.time.LocalDate;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String regnumber;
    private LocalDate manufactureDate;
    private State state;
    private int rentalId;

    public Car() {

    }

    public Car(String brand_, String model_, String regnumber_, LocalDate manufactureDate, State state, int rentalId) {
	super();
	this.brand = brand_;
	this.model = model_;
	this.regnumber = regnumber_;
	this.manufactureDate = manufactureDate;
	this.state = state;
	this.rentalId = rentalId;
    }

    public Car(int id, String brand_, String model_, String regnumber_, LocalDate manufactureDate, State state) {
	super();
	this.id = id;
	this.brand = brand_;
	this.model = model_;
	this.regnumber = regnumber_;
	this.manufactureDate = manufactureDate;
	this.state = state;
    }

    public Car(int id, String brand_, String model_, String regnumber_, LocalDate manufactureDate, State state, int rentalId) {
	super();
	this.id = id;
	this.brand = brand_;
	this.model = model_;
	this.regnumber = regnumber_;
	this.manufactureDate = manufactureDate;
	this.state = state;
	this.rentalId = rentalId;
    }

 
	public String toString() {
		return "Cars [id=" + id + ", brand=" + brand + ", model=" + model + ", regnumber=" + regnumber
				+ ", manufactureDate=" + manufactureDate + ", state=" + state + ", rentalId=" + rentalId + "]";
	}
    
    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getLabel() {
	return brand;
    }

    public void setLabel(String brand_) {
	this.brand = brand_;
    }

    public LocalDate getManufactureDate() {
	return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
	this.manufactureDate = manufactureDate;
    }

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
    }

    public int getRentalId() {
	return rentalId;
    }

    public void setRentalId(int rentalId) {
	this.rentalId = rentalId;
    }

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegnumber() {
		return regnumber;
	}

	public void setRegnumber(String regnumber) {
		this.regnumber = regnumber;
	}
}
