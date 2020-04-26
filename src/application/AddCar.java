package application;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import dbmanager.*;
import models.*;

public class AddCar extends JPanel {

    public AddCar() {
	setVisible(true);
	List<Rental> rentals = DatabaseManager.getRental();
	
	
	GroupLayout layout = new GroupLayout(this);
	JTextField brand = new JTextField();
	JTextField model = new JTextField();
	JTextField regnumber = new JTextField();
	JTextField date = new JTextField(LocalDate.now().toString());
	JComboBox<State> state = new JComboBox<State>(State.values());
	JComboBox<String> rental = new JComboBox<String>(rentals.stream().map(r -> {
	    return r.getName();
	}).toArray(String[]::new));
	
	JButton addButton = new JButton("Hozzáadás");
	JLabel brandLabel = new JLabel("Márka:");
	JLabel modelLabel = new JLabel("Model:");
	JLabel regnumberLabel = new JLabel("Rendszám:");
	JLabel dateLabel = new JLabel("Gyártási dátum:");
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BorderLayout(20, 20));
	buttonPanel.add(addButton);

	layout.setAutoCreateGaps(true);
	layout.setAutoCreateContainerGaps(true);
	setLayout(layout);

	layout.setVerticalGroup(layout.createSequentialGroup()
		.addGroup(layout.createParallelGroup().addComponent(brandLabel).addComponent(brand))
		.addGroup(layout.createParallelGroup().addComponent(modelLabel).addComponent(model))
		.addGroup(layout.createParallelGroup().addComponent(regnumberLabel).addComponent(regnumber))
		.addGroup(layout.createParallelGroup().addComponent(dateLabel).addComponent(date)).addComponent(state)
		.addComponent(rental).addComponent(buttonPanel));

	layout.setHorizontalGroup(layout.createSequentialGroup()
		.addGroup(layout.createParallelGroup()
			.addGroup(layout.createSequentialGroup().addComponent(brandLabel).addComponent(brand))
			.addGroup(layout.createSequentialGroup().addComponent(modelLabel).addComponent(model))
			.addGroup(layout.createSequentialGroup().addComponent(regnumberLabel).addComponent(regnumber))
			.addGroup(layout.createSequentialGroup().addComponent(dateLabel).addComponent(date))
			.addComponent(state).addComponent(rental).addComponent(buttonPanel)));

	dateLabel.setVerticalAlignment(SwingConstants.CENTER);
	dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
	
	brandLabel.setVerticalAlignment(SwingConstants.CENTER);
	brandLabel.setHorizontalAlignment(SwingConstants.LEFT);
	
	modelLabel.setVerticalAlignment(SwingConstants.CENTER);
	modelLabel.setHorizontalAlignment(SwingConstants.LEFT);
	
	regnumberLabel.setVerticalAlignment(SwingConstants.CENTER);
	regnumberLabel.setHorizontalAlignment(SwingConstants.LEFT);
	
	

	addButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if (brand.getText().isEmpty() || date.getText().isEmpty() ||  model.getText().isEmpty() || regnumber.getText().isEmpty() ) {
		    JOptionPane.showMessageDialog(AddCar.this, "Minden mezõnek ki kell lenni töltve!", "Hiba",
			    JOptionPane.ERROR_MESSAGE);
		    return;
		}
		try {
		    DatabaseManager.addCar(new Car(brand.getText(),model.getText(),regnumber.getText(), LocalDate.parse(date.getText()),
			    State.values()[state.getSelectedIndex()], rentals.get(rental.getSelectedIndex()).getId()));
		} catch (DateTimeParseException e) {
		    JOptionPane.showMessageDialog(AddCar.this, "Hibás dátum formátum!", "Hiba",
			    JOptionPane.ERROR_MESSAGE);
		    return;
		}

	    }
	});
    }

}
