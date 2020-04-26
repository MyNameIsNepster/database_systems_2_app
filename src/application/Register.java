package application;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbmanager.*;
import models.User;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;

public class Register extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password1;
    private JPasswordField password2;
    private JTextPane txtpnFelhasznlnv;
    private JTextPane txtpnJelsz;
    private JTextPane txtpnFelhasznlnv_2;

    public Register() {
    	setTitle("Regisztr\u00E1ci\u00F3");

	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	contentPane = new JPanel();
	contentPane.setBackground(Color.LIGHT_GRAY);
	setBounds(0, 0, 542, 300);
	contentPane.setLayout(null);
	setLocationRelativeTo(null);
	setContentPane(contentPane);

	username = new JTextField("");
	username.setBounds(50, 50, 200, 30);
	contentPane.add(username);

	password1 = new JPasswordField();
	password1.setBounds(280, 50, 200, 30);
	contentPane.add(password1);

	password2 = new JPasswordField();
	password2.setBounds(280, 122, 200, 30);
	contentPane.add(password2);

	JButton register = new JButton("Regisztr\u00E1ci\u00F3");
	register.setFont(new Font("Tahoma", Font.PLAIN, 14));
	register.setBounds(50, 197, 200, 38);
	contentPane.add(register);

	JButton login = new JButton("Vissza a bejelentkez\u00E9shez");
	login.setFont(new Font("Tahoma", Font.PLAIN, 14));
	login.setBounds(280, 197, 200, 38);
	contentPane.add(login);
	
	txtpnFelhasznlnv = new JTextPane();
	txtpnFelhasznlnv.setFont(new Font("Tahoma", Font.PLAIN, 14));
	txtpnFelhasznlnv.setBackground(Color.LIGHT_GRAY);
	txtpnFelhasznlnv.setEditable(false);
	txtpnFelhasznlnv.setText("Felhaszn\u00E1l\u00F3n\u00E9v:");
	txtpnFelhasznlnv.setBounds(50, 19, 200, 20);
	contentPane.add(txtpnFelhasznlnv);
	
	txtpnJelsz = new JTextPane();
	txtpnJelsz.setFont(new Font("Tahoma", Font.PLAIN, 14));
	txtpnJelsz.setText("Jelsz\u00F3:");
	txtpnJelsz.setEditable(false);
	txtpnJelsz.setBackground(Color.LIGHT_GRAY);
	txtpnJelsz.setBounds(280, 19, 200, 20);
	contentPane.add(txtpnJelsz);
	
	txtpnFelhasznlnv_2 = new JTextPane();
	txtpnFelhasznlnv_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
	txtpnFelhasznlnv_2.setText("Jelsz\u00F3 m\u00E9gegyszer:");
	txtpnFelhasznlnv_2.setEditable(false);
	txtpnFelhasznlnv_2.setBackground(Color.LIGHT_GRAY);
	txtpnFelhasznlnv_2.setBounds(280, 91, 200, 20);
	contentPane.add(txtpnFelhasznlnv_2);

	setResizable(false);

	login.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		new Login();
		dispose();

	    }
	});

	register.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		if (String.valueOf(password1.getPassword()).equals(String.valueOf(password2.getPassword()))) {
		    try {
			if (UserManager.register(new User(username.getText(), String.valueOf(password1.getPassword())))) {
			    new Login();
			    dispose();
			}
		    } catch (Exception e) {
			JOptionPane.showMessageDialog(Register.this, e.getMessage(), "Hiba",
				JOptionPane.ERROR_MESSAGE);
			password1.setText(null);
			password2.setText(null);
			username.setText(null);
		    }
		} else {
		    JOptionPane.showMessageDialog(Register.this, "A jelszavak nem egyeznek!", "Hiba",
			    JOptionPane.ERROR_MESSAGE);
		    password1.setText(null);
		    password2.setText(null);
		}
	    }
	});

	setVisible(true);
    }
}
