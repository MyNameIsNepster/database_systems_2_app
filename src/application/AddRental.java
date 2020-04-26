package application;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dbmanager.*;
import models.Car;
import models.Rental;
import models.State;

public class AddRental extends JPanel {

    public AddRental() {
	setVisible(true);
	GroupLayout layout = new GroupLayout(this);
	JTextField name = new JTextField();
	JTextField date = new JTextField(LocalDate.now().toString());
	JTextField city = new JTextField();
	JTextField employees = new JTextField();

	JButton addButton = new JButton("Hozzáadás");
	JLabel nameLabel = new JLabel("Kölcsönzõ neve:");
	JLabel dateLabel = new JLabel("Alapítási dátum:");
	JLabel employeesLabel = new JLabel("Dolgozók száma:");
	JLabel cityLabel = new JLabel("Város:");

	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BorderLayout(20, 20));
	buttonPanel.add(addButton);

	layout.setAutoCreateGaps(true);
	layout.setAutoCreateContainerGaps(true);
	setLayout(layout);

	layout.setVerticalGroup(layout.createSequentialGroup()
		.addGroup(layout.createParallelGroup().addComponent(nameLabel).addComponent(name))
		.addGroup(layout.createParallelGroup().addComponent(cityLabel).addComponent(city))
		.addGroup(layout.createParallelGroup().addComponent(dateLabel).addComponent(date))
		.addGroup(layout.createParallelGroup().addComponent(employeesLabel).addComponent(employees))
		.addComponent(buttonPanel));
	layout.setHorizontalGroup(layout.createParallelGroup()
		.addGroup(layout.createSequentialGroup().addComponent(nameLabel).addComponent(name))
		.addGroup(layout.createSequentialGroup().addComponent(cityLabel).addComponent(city))
		.addGroup(layout.createSequentialGroup().addComponent(dateLabel).addComponent(date))
		.addGroup(layout.createSequentialGroup().addComponent(employeesLabel).addComponent(employees))
		.addComponent(buttonPanel));

	addButton.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		if (name.getText().isEmpty() || date.getText().isEmpty() || city.getText().isEmpty()
			|| employees.getText().isEmpty()) {
		    JOptionPane.showMessageDialog(AddRental.this, "Minden mezõnek ki kell lenni töltve!", "Hiba",
			    JOptionPane.ERROR_MESSAGE);
		    return;
		}
		try {
		    DatabaseManager.addRental(new Rental(name.getText(), city.getText(),
			    Integer.valueOf(employees.getText()), LocalDate.parse(date.getText())));
		} catch (DateTimeParseException e) {
		    JOptionPane.showMessageDialog(AddRental.this, "Hibás dátum formátum!", "Hiba",
			    JOptionPane.ERROR_MESSAGE);
		    return;
		} catch (NumberFormatException e) {
		    JOptionPane.showMessageDialog(AddRental.this, "Hibás szám formátum!", "Hiba",
			    JOptionPane.ERROR_MESSAGE);
		}

	    }
	});
    }

}
