package application;


import models.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import dbmanager.*;

import javax.swing.JTextField;
import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldLogin;

    public Login() {
    	setTitle("Bejelentkez\u00E9s");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(0, 0, 564, 199);
	contentPane = new JPanel();
	contentPane.setBackground(Color.LIGHT_GRAY);
	setContentPane(contentPane);
	contentPane.setLayout(null);
	setLocationRelativeTo(null);
	textFieldLogin = new JTextField();
	textFieldLogin.setBounds(20, 38, 250, 25);
	contentPane.add(textFieldLogin);
	textFieldLogin.setColumns(10);

	JPasswordField passwordField = new JPasswordField();
	passwordField.setBounds(20, 97, 250, 25);
	contentPane.add(passwordField);

	JButton btnLogin = new JButton("Bejelentkez\u00E9s");
	btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
	btnLogin.setBounds(332, 25, 200, 50);
	contentPane.add(btnLogin);

	JButton btnRegister = new JButton("Regiszt\u00E1ci\u00F3");
	btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 14));
	btnRegister.setBounds(358, 97, 151, 25);
	contentPane.add(btnRegister);
	
	JTextPane txtpnFelhasznlnv = new JTextPane();
	txtpnFelhasznlnv.setFont(new Font("Tahoma", Font.PLAIN, 14));
	txtpnFelhasznlnv.setEditable(false);
	txtpnFelhasznlnv.setBackground(Color.LIGHT_GRAY);
	txtpnFelhasznlnv.setText("Felhaszn\u00E1l\u00F3n\u00E9v:");
	txtpnFelhasznlnv.setBounds(20, 11, 200, 20);
	contentPane.add(txtpnFelhasznlnv);
	
	JTextPane txtpnJelsz = new JTextPane();
	txtpnJelsz.setFont(new Font("Tahoma", Font.PLAIN, 14));
	txtpnJelsz.setEditable(false);
	txtpnJelsz.setText("Jelsz\u00F3:");
	txtpnJelsz.setBackground(Color.LIGHT_GRAY);
	txtpnJelsz.setBounds(20, 74, 200, 20);
	contentPane.add(txtpnJelsz);

	setResizable(false);
	setVisible(true);

	btnRegister.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent arg0) {
		new Register();
		dispose();
	    }
	});

	passwordField.addKeyListener(new KeyListener() {

	    public void keyTyped(KeyEvent arg0) {

	    }

	    public void keyReleased(KeyEvent arg0) {

	    }

	    public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_ENTER) {
		    User user = null;
		    try {
			if (textFieldLogin.getText().isEmpty()
				|| String.valueOf(passwordField.getPassword()).isEmpty()) {
			    throw new LoginException("Hibás felhasználónév / jelszó!");
			}

			user = new User(textFieldLogin.getText(), String.valueOf(passwordField.getPassword()));
		    } catch (Exception e1) {
			JOptionPane.showMessageDialog(Login.this, e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
			textFieldLogin.setText(null);
			passwordField.setText(null);
			return;
		    }

		    try {
			if (UserManager.login(user)) {
			    new App();
			    dispose();
			} else {
			    JOptionPane.showMessageDialog(Login.this, "Hibás jelszó!", "Hiba",
				    JOptionPane.ERROR_MESSAGE);
			    textFieldLogin.setText(null);
			    passwordField.setText(null);
			    return;
			}
		    } catch (Exception e1) {
			JOptionPane.showMessageDialog(Login.this, e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		    }
		}

	    }
	});

	btnLogin.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		User user = null;
		try {
		    if (textFieldLogin.getText().isEmpty() || String.valueOf(passwordField.getPassword()).isEmpty()) {
			throw new LoginException("Hibás felhasználónév / jelszó!");
		    }

		    user = new User(textFieldLogin.getText(), String.valueOf(passwordField.getPassword()));
		} catch (Exception e1) {
		    JOptionPane.showMessageDialog(Login.this, e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		    textFieldLogin.setText(null);
		    passwordField.setText(null);
		    return;
		}

		try {
		    if (UserManager.login(user)) {
			new App();
			dispose();
		    } else {
			JOptionPane.showMessageDialog(Login.this, "Hibás jelszó!", "Hiba",
				JOptionPane.ERROR_MESSAGE);
			textFieldLogin.setText(null);
			passwordField.setText(null);
			return;
		    }
		} catch (Exception e1) {
		    JOptionPane.showMessageDialog(Login.this, e1.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
		}

	    }
	});
    }
}
