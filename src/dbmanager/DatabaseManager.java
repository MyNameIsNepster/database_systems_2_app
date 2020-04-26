package dbmanager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Car;
import models.Rental;
import models.State;

public class DatabaseManager {
	
    private static Connection connect() {
	Connection connection = null;
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    connection = DriverManager.getConnection
	    ("jdbc:mysql://localhost/carrental?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "User", "User0123456789");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return connection;
    }

    public static List<Car> getCar() {
	Connection connection = connect();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery("SELECT * FROM CAR");
	    List<Car> cars = new ArrayList<Car>();
	    while (resultSet.next()) {
		cars.add(new Car(resultSet.getInt("id"), resultSet.getString("brand"), resultSet.getString("model"),
				resultSet.getString("regnumber"), resultSet.getDate("manufacture_date").toLocalDate(),
			State.valueOf(resultSet.getString("state")), resultSet.getInt("rental_id")));
	    }
	    connection.close();
	    return cars;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static Car getCar(int id) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM CAR WHERE id = ?");
	    statement.setInt(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		Car cars = new Car(resultSet.getInt("id"), resultSet.getString("brand"), resultSet.getString("model"),
				resultSet.getString("regnumber"), resultSet.getDate("manufacture_date").toLocalDate(),
			State.valueOf(resultSet.getString("state")), resultSet.getInt("rental_id"));
		connection.close();
		return cars;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static List<Car> getCar(String brand) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM CAR WHERE brand = ?");
	    statement.setString(1, brand);
	    ResultSet resultSet = statement.executeQuery();
	    List<Car> cars = new ArrayList<Car>();
	    while (resultSet.next()) {
		cars.add(new Car(resultSet.getInt("id"), resultSet.getString("brand"), resultSet.getString("model"),
				resultSet.getString("regnumber"), resultSet.getDate("manufacture_date").toLocalDate(),
			State.valueOf(resultSet.getString("state")), resultSet.getInt("rental_id")));
	    }
	    connection.close();
	    return cars;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static List<Car> getCar(String brand, int rental) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection
		    .prepareStatement("SELECT * FROM CAR WHERE brand = ? AND rental_id = ?");
	    statement.setString(1, brand);
	    statement.setInt(2, rental);
	    ResultSet resultSet = statement.executeQuery();
	    List<Car> cars = new ArrayList<Car>();
	    while (resultSet.next()) {
		cars.add(new Car(resultSet.getInt("id"), resultSet.getString("brand"), resultSet.getString("model"),
				resultSet.getString("regnumber"), resultSet.getDate("manufacture_date").toLocalDate(),
			State.valueOf(resultSet.getString("state")), resultSet.getInt("rental_id")));
	    }
	    connection.close();
	    return cars;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static List<Rental> getRental() {
	Connection connection = connect();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery("SELECT * FROM Rental");
	    List<Rental> rentals = new ArrayList<Rental>();
	    while (resultSet.next()) {
		rentals.add(new Rental(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("city"),
			resultSet.getInt("employees"), resultSet.getDate("date_founded").toLocalDate()));
	    }
	    connection.close();
	    return rentals;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static String getRentalName(int id) {
	Connection connection = connect();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery("SELECT name FROM Rental WHERE id = " + id);
	    if (resultSet.next()) {
		String name = resultSet.getString(1);
		connection.close();
		return name;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static int getRentalID(String name) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT id FROM Rental WHERE name = ?");
	    statement.setString(1, name);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		int id = resultSet.getInt(1);
		connection.close();
		return id;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return 0;
    }

    public static void updateRental(Rental rental) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection.prepareStatement(
		    "UPDATE Rental SET name = ?, city = ?, employees = ?, date_founded = ? WHERE id = "
			    + rental.getId());
	    statement.setString(1, rental.getName());
	    statement.setString(2, rental.getCity());
	    statement.setInt(3, rental.getEmployees());
	    statement.setDate(4, Date.valueOf(rental.getDateFounded()));
	    statement.execute();
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void updateCar(Car cars) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection.prepareStatement(
		    "UPDATE CAR SET brand=?,model=?,regnumber=?, manufacture_date = ?, state = ?, rental_id = ? WHERE id = " + cars.getId());
	    statement.setString(1, cars.getLabel());
	    statement.setString(2, cars.getModel());
	    statement.setString(3, cars.getRegnumber());
	    statement.setDate(4, Date.valueOf(cars.getManufactureDate()));
	    statement.setString(5, cars.getState().toString());
	    statement.setInt(6, cars.getRentalId());
	    statement.execute();
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void removeCar(int id) {
	Connection connection = connect();

	try {
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("DELETE FROM CAR WHERE id = " + id);
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void removeRental(int id) {
	Connection connection = connect();

	try {
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("DELETE FROM CAR WHERE rental_id = " + id);
	    statement.executeUpdate("DELETE FROM Rental WHERE id = " + id);
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void addCar(Car cars) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection.prepareStatement(
		    "INSERT INTO CAR (brand, model, regnumber, manufacture_date, state, rental_id) VALUES (?, ?, ?, ?, ?, ?)");
	    statement.setString(1, cars.getLabel());
	    statement.setString(2, cars.getModel());
	    statement.setString(3, cars.getRegnumber());
	    statement.setDate(4, Date.valueOf(cars.getManufactureDate()));
	    statement.setString(5, cars.getState().toString());
	    statement.setInt(6, cars.getRentalId());
	    statement.execute();
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void addRental(Rental rental) {
	Connection connection = connect();
	try {
	    PreparedStatement statement = connection
		    .prepareStatement("INSERT INTO Rental (name, city, employees, date_founded) VALUES (?, ?, ?, ?)");
	    statement.setString(1, rental.getName());
	    statement.setString(2, rental.getCity());
	    statement.setInt(3, rental.getEmployees());
	    statement.setDate(4, Date.valueOf(rental.getDateFounded()));
	    statement.execute();
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static List<Car> getCarByRental(int id) {
	Connection connection = connect();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery(
		    "SELECT Car.id AS id, Car.brand ,Car.model ,Car.regnumber , Car.manufacture_date, Car.state, Car.rental_id FROM Car INNER JOIN Rental ON Rental.id = Car.rental_id WHERE Rental.id = "
			    + id);

	    List<Car> cars = new ArrayList<Car>();
	    while (resultSet.next()) {
		Car car = new Car(resultSet.getInt("id"), resultSet.getString("brand"), resultSet.getString("model"),
				resultSet.getString("regnumber"), resultSet.getDate("manufacture_date").toLocalDate(),
			State.valueOf(resultSet.getString("state")));
		car.setRentalId(resultSet.getInt("rental_id"));
		cars.add(car);
	    }
	    return cars;

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

}
