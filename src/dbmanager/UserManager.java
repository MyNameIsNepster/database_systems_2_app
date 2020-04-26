package dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public class UserManager {

    public static boolean login(User user) throws Exception {
	User temp = getUser(user.getUserName());
	if (temp != null) {
	    if (temp.getPassword().equals(StringUtil.sha512(user.getPassword()))) {
		return true;
	    }
	} else {
	    throw new Exception("Nincs ilyen felhasználó!");
	}
	return false;
    }

    public static boolean register(User user) throws Exception {
	if (user.getPassword().length() < 8) {
	    throw new Exception("A jelszónak legalább 8 jegyûnek kell lennie!");
	}
	if (getUser(user.getUserName()) == null) {
	    addUser(user);
	    return true;
	}
	throw new Exception("Ez a felhasználónév már foglalt!");
    }

    private static User getUser(String username) {
	Connection connection = connect();
	ResultSet rs = null;

	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?");
	    statement.setString(1, username);
	    rs = statement.executeQuery();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	User user = new User();

	try {
	    if (rs.next()) {
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		connection.close();
		return user;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	try {
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;

    }

    private static void addUser(User user) {
	Connection connection = connect();

	try {
	    PreparedStatement statement = connection
		    .prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)");
	    statement.setString(1, user.getUserName());
	    statement.setString(2, StringUtil.sha512(user.getPassword()));
	    statement.execute();
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    private static Connection connect() {
	Connection connection = null;
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    connection = DriverManager.getConnection("jdbc:mysql://localhost/carrental?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "User", "User0123456789");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return connection;
    }
}
