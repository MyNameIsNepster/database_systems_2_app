package application;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dbmanager.*;
import models.Car;
import models.Rental;
import models.State;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class App extends JFrame {

    private JPanel contentPane;
    private JTable table;
    
    public static void main(String[] args) {
	JFrame app = new App();
	app.setVisible(true);
    }

    public App() {
    	setTitle("Aut\u00F3k\u00F6lcs\u00F6nz\u0151 nyilv\u00E1ntart\u00F3");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(0, 0, 900, 370);
	getContentPane().setLayout(new BorderLayout());
	setResizable(false);
	setVisible(true);
	setLocationRelativeTo(null);

	contentPane = new JPanel();
	contentPane.setVisible(true);
	contentPane.setLayout(new BorderLayout());
	getContentPane().add(contentPane);
	contentPane.setVisible(true);

	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	JMenu addMenu = new JMenu("Hozz\u00E1ad\u00E1s");
	menuBar.add(addMenu);

	JMenuItem addCar = new JMenuItem("Aut\u00F3 hozz\u00E1ad\u00E1sa");
	addMenu.add(addCar);
	addCar.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		contentPane.removeAll();
		invalidate();
		contentPane.add(new AddCar(), BorderLayout.CENTER);
		validate();
	    }
	});

	JMenuItem addRental = new JMenuItem("Aut\u00F3k\u00F6lcs\u00F6nz\u0151 hozz\u00E1ad\u00E1sa");
	addMenu.add(addRental);
	addRental.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		contentPane.removeAll();
		invalidate();
		contentPane.add(new AddRental(), BorderLayout.CENTER);
		validate();

	    }
	});

	JMenu listMenu = new JMenu("Kilist\u00E1z\u00E1s");
	menuBar.add(listMenu);

	JMenuItem listByRental = new JMenuItem("Aut\u00F3k list\u00E1ja k\u00F6lcs\u00F6nz\u0151k szerint");
	listMenu.add(listByRental);
	listByRental.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		List<Rental> rentals = DatabaseManager.getRental();
		JComboBox<String> rentalBox = new JComboBox<String>(
			rentals.stream().map(p -> p.getName()).toArray(String[]::new));

		contentPane.removeAll();
		invalidate();
		JScrollPane carList = new JScrollPane();
		carList.setVisible(true);
		
		JPanel remove = new JPanel();
		remove.setLayout(new GridLayout());

		JButton removeButton = new JButton("Eltávolítás");
		contentPane.add(remove, BorderLayout.SOUTH);

		

		rentalBox.addActionListener(new ActionListener() {


		    public void actionPerformed(ActionEvent arg0) {
			List<Car> cars = DatabaseManager.getCarByRental(rentals.get(rentalBox.getSelectedIndex()).getId());
			DefaultTableModel carModel = new DefaultTableModel() {

			    private static final long serialVersionUID = 1L;
   				    
			    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				Car row = cars.get(rowIndex);
				if (0 == columnIndex) {
				    row.setBrand((String) aValue);
				 
				}   else if (1 == columnIndex) {
					row.setModel((String) aValue);    
				    
				}   else if (2 == columnIndex) {
				    row.setRegnumber((String) aValue);    
				    
				} else if (3 == columnIndex) {
				    try {
					row.setManufactureDate(LocalDate.parse((String) aValue));
				    } catch (DateTimeParseException e) {
					JOptionPane.showMessageDialog(App.this, (String) aValue + " nem dátum!", "Hiba",
						JOptionPane.ERROR_MESSAGE);
					return;
				    }
				} else if (4 == columnIndex) {
				    try {
					row.setState(State.valueOf(((String) aValue).toUpperCase()));
				    } catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(App.this, (String) aValue + " nem állapot!", "Hiba",
						JOptionPane.ERROR_MESSAGE);
					return;
				    }
				} else if (5 == columnIndex) {
				    int rentalID = DatabaseManager.getRentalID((String) aValue);
				    if (rentalID > 0) {
					row.setRentalId(rentalID);
				    } else {
					JOptionPane.showMessageDialog(App.this, (String) aValue + " nem kölcsönzõ!", "Hiba",
						JOptionPane.ERROR_MESSAGE);
					return;
				    }
				}
				DatabaseManager.updateCar(row);
			    }
	   
			    		 
			    public Object getValueAt(int rowIndex, int columnIndex) {
				Car row = cars.get(rowIndex);
				if (0 == columnIndex) {
				    return row.getBrand();			    
				} else if (1 == columnIndex) {
					return row.getModel();
				} else if (2 == columnIndex) {
					return row.getRegnumber();
				} else if (3 == columnIndex) {
				    return row.getManufactureDate();
				} else if (4 == columnIndex) {
				    return row.getState().toString().toLowerCase();
				} else if (5 == columnIndex) {
				    return (row.getRentalId() == 0 ? "Nem definiált!"
					    : DatabaseManager.getRentalName(row.getRentalId()));
				}
				return null;
			    }
			};
			carModel.setColumnCount(6);
			carModel.setColumnIdentifiers(new String[] { "Márka","Modell","Rendszám", "Évjárat", "Státusz", "Köncsönzõ" });
			for (Car car : cars) {
			    carModel.addRow(new String[] { car.getBrand(), car.getModel(),car.getRegnumber(),
			    		car.getManufactureDate().toString(),
				    car.getState().toString().toLowerCase(),
				    DatabaseManager.getRentalName(car.getRentalId()) });
			}
			table = new JTable(carModel);
			table.setAutoCreateRowSorter(true);
			carList.setVisible(true);
			carList.setViewportView(table);
			
			removeButton.addActionListener(new ActionListener() {

		
			    public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row > -1) {		
				    listByRental.getActionListeners()[0].actionPerformed(new ActionEvent(this, 1, "button"));
				}
			    }
			});
			
		    }
		});

		remove.add(removeButton);
		remove.add(rentalBox);
		remove.setVisible(true);

		contentPane.add(carList, BorderLayout.CENTER);
		contentPane.add(remove, BorderLayout.SOUTH);
		validate();
	    }
	});

	JMenuItem listCar = new JMenuItem("Aut\u00F3k list\u00E1ja");
	listMenu.add(listCar);
	listCar.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		contentPane.removeAll();
		invalidate();
		JScrollPane carList = new JScrollPane();
		List<Car> cars = DatabaseManager.getCar();
		DefaultTableModel carModel = new DefaultTableModel() {

		    private static final long serialVersionUID = 1L;
		
		    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Car row = cars.get(rowIndex);
			if (0 == columnIndex) {
			    row.setBrand((String) aValue);
			 
			}   else if (1 == columnIndex) {
				row.setModel((String) aValue);    
			    
			}   else if (2 == columnIndex) {
			    row.setRegnumber((String) aValue);    
			    
			} else if (3 == columnIndex) {
			    try {
				row.setManufactureDate(LocalDate.parse((String) aValue));
			    } catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(App.this, (String) aValue + " nem dátum!", "Hiba",
					JOptionPane.ERROR_MESSAGE);
				return;
			    }
			} else if (4 == columnIndex) {
			    try {
				row.setState(State.valueOf(((String) aValue).toUpperCase()));
			    } catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(App.this, (String) aValue + " nem státusz!", "Hiba",
					JOptionPane.ERROR_MESSAGE);
				return;
			    }
			} else if (5 == columnIndex) {
			    int rentalID = DatabaseManager.getRentalID((String) aValue);
			    if (rentalID > 0) {
				row.setRentalId(rentalID);
			    } else {
				JOptionPane.showMessageDialog(App.this, (String) aValue + " nem kölcsönzõ!", "Hiba",
					JOptionPane.ERROR_MESSAGE);
				return;
			    }
			}
			DatabaseManager.updateCar(row);
		    }

	
		    public Object getValueAt(int rowIndex, int columnIndex) {
			Car row = cars.get(rowIndex);
			if (0 == columnIndex) {
			    return row.getBrand();			    
			} else if (1 == columnIndex) {
				return row.getModel();
			} else if (2 == columnIndex) {
				return row.getRegnumber();
			} else if (3 == columnIndex) {
			    return row.getManufactureDate();
			} else if (4 == columnIndex) {
			    return row.getState().toString().toLowerCase();
			} else if (5 == columnIndex) {
			    return (row.getRentalId() == 0 ? "Nem definiált!"
				    : DatabaseManager.getRentalName(row.getRentalId()));
			}
			return null;
		    }
		};
		carModel.setColumnCount(6);
		carModel.setColumnIdentifiers(new String[] {  "Márka","Modell","Rendszám", "Évjárat", "Státusz", "Köncsönzõ"  });
		for (Car car : cars) {
			carModel.addRow(new String[] { car.getBrand(), car.getModel(),car.getRegnumber(),
		    		car.getManufactureDate().toString(),
			    car.getState().toString().toLowerCase(),
			    DatabaseManager.getRentalName(car.getRentalId()) });
		}
		table = new JTable(carModel);
		table.setAutoCreateRowSorter(true);
		carList.setVisible(true);
		carList.setViewportView(table);

		JButton remove = new JButton("Eltávolítás");
		contentPane.add(remove, BorderLayout.SOUTH);

		remove.addActionListener(new ActionListener() {

	
		    public void actionPerformed(ActionEvent arg0) {
			int row = table.getSelectedRow();
			if (row > -1) {
			    DatabaseManager.removeCar(cars.get(row).getId());
			    listCar.getActionListeners()[0].actionPerformed(new ActionEvent(this, 1, "button"));
			}
		    }
		});

		contentPane.add(carList, BorderLayout.CENTER);
		contentPane.add(remove, BorderLayout.SOUTH);
		validate();

	    }
	});

	JMenuItem listRental = new JMenuItem("K\u00F6lcs\u00F6nz\u0151k list\u00E1ja");
	listMenu.add(listRental);
	listRental.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		contentPane.removeAll();
		invalidate();
		JScrollPane rentalList = new JScrollPane();
		List<Rental> rentals = DatabaseManager.getRental();
		DefaultTableModel rentalModel = new DefaultTableModel() {

		    private static final long serialVersionUID = 1L;
		    
		    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Rental row = rentals.get(rowIndex);
			if (0 == columnIndex) {
			    row.setName((String) aValue);
			} else if (1 == columnIndex) {
			    row.setCity((String) aValue);
			} else if (2 == columnIndex) {
				
			try { 
				row.setEmployees (Integer.parseInt((String) aValue));
					
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(App.this, (String) aValue + " nem szám!", "Hiba",
						JOptionPane.ERROR_MESSAGE);
					return;}
				    
			    
			    
			} else if (3 == columnIndex) {
			    try {
				row.setDateFounded(LocalDate.parse((String) aValue));
			    } catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(App.this, (String) aValue + " nem dátum!", "Hiba",
					JOptionPane.ERROR_MESSAGE);
				return;}
			    }
			DatabaseManager.updateRental(row);
		    }

		    public Object getValueAt(int rowIndex, int columnIndex) {
			Rental row = rentals.get(rowIndex);
			if (0 == columnIndex) {
			    return row.getName();
			} else if (1 == columnIndex) {
			    return row.getCity();
			} else if (2 == columnIndex) {
			    return row.getEmployees();
			} else if (3 == columnIndex) {
			    return row.getDateFounded();
			}
			return null;
		    }
		};
		rentalModel.setColumnCount(4);
		rentalModel.setColumnIdentifiers(new String[] { "Kölcsönzõ neve", "Város", "Alkalmazottak", "Alapítási dátum" });
		for (Rental rental : rentals) {
		    rentalModel.addRow(new String[] { rental.getName(), rental.getCity(),
			    Integer.toString(rental.getEmployees()), rental.getDateFounded().toString() });
		}
		table = new JTable(rentalModel);
		table.setAutoCreateRowSorter(true);

		JButton remove = new JButton("Eltávolítás");
		contentPane.add(remove, BorderLayout.SOUTH);

		remove.addActionListener(new ActionListener() {

		    public void actionPerformed(ActionEvent arg0) {
			int row = table.getSelectedRow();
			if (row > -1) {
			    DatabaseManager.removeRental(rentals.get(row).getId());		   
			    listRental.getActionListeners()[0].actionPerformed(new ActionEvent(this, 1, "button"));
			}

		    }
		});

		contentPane.removeAll();
		rentalList.setViewportView(table);
		contentPane.add(rentalList, BorderLayout.CENTER);
		contentPane.add(remove, BorderLayout.SOUTH);
		validate();
	    }
	});
    }

}
