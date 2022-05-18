package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Business.Validator;
import Connection.ConnectionFactory;

public class Register {
    private JTextField userTxt;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JLabel jcomp5;
    private JLabel jcomp6;
    private JLabel jcomp7;
    private JLabel jcomp8;
    private JButton registerButton;
    private JButton backBtn;
    private JLabel passwordErrorTxt;
    private JLabel passwordTooShort;
    private JLabel emailErrorTxt;
    private JLabel emailExistsTxt;
    private JFrame frame;

    private static Register register;

    private Register(){
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(550, 500);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //construct components
        passwordField1 = new JPasswordField (5);
        userTxt = new JTextField (5);
        passwordField2 = new JPasswordField (5);
        registerButton = new JButton ("Register");
        jcomp5 = new JLabel ("Email:");
        jcomp6 = new JLabel ("Password:");
        jcomp7 = new JLabel ("Confirm Password:");
        jcomp8 = new JLabel ("Sign Up!");
        backBtn = new JButton ("Back");
        passwordErrorTxt = new JLabel ("Passwords don't match!");
        emailErrorTxt = new JLabel ("Wrong email format!");
        emailExistsTxt = new JLabel("Email already in use!");
        passwordTooShort = new JLabel("Password must be at least 7 characters long!");


        //add components
        frame.add (passwordField1);
        frame.add (userTxt);
        frame.add (passwordField2);
        frame.add (registerButton);
        frame.add (jcomp5);
        frame.add (jcomp6);
        frame.add (jcomp7);
        frame.add (jcomp8);
        frame.add (backBtn);
        frame.add (passwordErrorTxt);
        frame.add (emailErrorTxt);
        frame.add (emailExistsTxt);
        frame.add (passwordTooShort);

        //set component bounds (only needed by Absolute Positioning)
        passwordField1.setBounds (215, 185, 260, 25);
        userTxt.setBounds (215, 120, 260, 25);
        passwordField2.setBounds (215, 250, 260, 25);
        registerButton.setBounds (210, 360, 260, 35);
        jcomp5.setBounds (55, 120, 100, 25);
        jcomp6.setBounds (55, 185, 100, 25);
        jcomp7.setBounds (55, 250, 135, 25);
        jcomp8.setBounds (280, 55, 115, 40);
        backBtn.setBounds (15, 15, 65, 35);
        passwordErrorTxt.setBounds (30, 310, 150, 25);
        passwordTooShort.setBounds (30, 310, 300, 25);
        emailErrorTxt.setBounds (270, 310, 140, 25);
        emailExistsTxt.setBounds(280,310,140,25);

        passwordErrorTxt.setVisible(false);
        emailErrorTxt.setVisible(false);
        emailExistsTxt.setVisible(false);
        passwordTooShort.setVisible(false);

        registerButton.addActionListener(new RegisterButtonActionListener());
        backBtn.addActionListener(new BackButtonActionListener());

        frame.setVisible(true);
    }

    public static Register getInstance(){
        if(register == null)
            register = new Register();
        return register;
    }

    private class RegisterButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordErrorTxt.setVisible(false);
            emailErrorTxt.setVisible(false);
            passwordTooShort.setVisible(false);
            emailExistsTxt.setVisible(false);

            try {
                Validator.validateEmail(userTxt.getText());
            }catch (IllegalArgumentException exception){
                emailErrorTxt.setVisible(true);
                return;
            }
            try{
                Validator.validatePassword(passwordField1.getText());
            }catch (IllegalArgumentException exception){
                passwordTooShort.setVisible(true);
                return;
            }

            if(!passwordField1.getText().equals(passwordField2.getText())){
                passwordErrorTxt.setVisible(true);
                return;
            }

            Connection connection = ConnectionFactory.getConnection();

            try {
                PreparedStatement nb = connection.prepareStatement("SELECT email FROM users WHERE email = ? ORDER BY id DESC LIMIT 1");
                nb.setString(1,userTxt.getText());
                ResultSet rs = nb.executeQuery();
                int id=0;
                if(rs.next()){
                    emailExistsTxt.setVisible(true);
                    return;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?)");
                PreparedStatement nb = connection.prepareStatement("SELECT id FROM users ORDER BY id DESC LIMIT 1");
                ResultSet rs = nb.executeQuery();
                int id=0;
                if(rs.next()){
                    id=rs.getInt(1);
                }
                statement.setInt(1,id+1);
                statement.setString(2,userTxt.getText());
                statement.setString(3,passwordField1.getText());
                statement.setString(4,"Client");
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ConnectionFactory.close(connection);

            setVisible(false);
            Login.getInstance().setVisible(true);
        }
    }

    private class BackButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            Login.getInstance().setVisible(true);
        }
    }
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}

