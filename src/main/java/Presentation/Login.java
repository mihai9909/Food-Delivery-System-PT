package Presentation;


import Business.DeliveryService;
import Business.Validator;
import Connection.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login{
    private JTextField userTxt;
    private JPasswordField password;
    private JButton logInButton;
    private JButton registerButton;
    private JLabel checkCredentialsText;
    private JLabel jcomp3;
    private JLabel jcomp4;
    private JLabel jcomp5;
    private JFrame frame;

    private static Login login;

    private Login(){

        frame = new JFrame("Log in");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        userTxt = new JTextField (5);
        password = new JPasswordField (5);
        jcomp3 = new JLabel ("Login");
        jcomp4 = new JLabel ("Email:");
        jcomp5 = new JLabel ("Password:");
        logInButton = new JButton ("Login");
        registerButton = new JButton ("Register");
        checkCredentialsText = new JLabel ("Check your credentials!");


        //add components
        frame.add (userTxt);
        frame.add (password);
        frame.add (jcomp3);
        frame.add (jcomp4);
        frame.add (jcomp5);
        frame.add (logInButton);
        frame.add (registerButton);
        frame.add (checkCredentialsText);

        //set component bounds (only needed by Absolute Positioning)
        userTxt.setBounds (70, 185, 365, 25);
        password.setBounds (70, 290, 365, 25);
        jcomp3.setBounds (220, 70, 105, 25);
        jcomp4.setBounds (70, 145, 100, 25);
        jcomp5.setBounds (70, 250, 100, 25);
        logInButton.setBounds (110, 370, 115, 35);
        registerButton.setBounds (270, 370, 120, 35);
        checkCredentialsText.setBounds (180, 330, 195, 25);

        logInButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());

        checkCredentialsText.setVisible(false);

        frame.setVisible(true);
    }

    public static Login getInstance(){
        if(login == null){
            login = new Login();
        }
        return login;
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentialsText.setVisible(false);
            String userText = userTxt.getText();
            String passwordText = password.getText();

            Connection connection = ConnectionFactory.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT privileges FROM users WHERE email = ? AND password = ?");
                statement.setString(1,userText);
                statement.setString(2,passwordText);
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    String privileges = rs.getString(1);
                    switch (privileges){
                        case "Administrator":
                            new Administrator();
                            break;
                        case "Employee":
                            //TODO: CLIENT VIEW
                            break;
                        default:
                            PreparedStatement st = connection.prepareStatement("SELECT id FROM users WHERE email = ? AND password = ?");
                            st.setString(1,userText);
                            st.setString(2,passwordText);
                            ResultSet resSet = st.executeQuery();
                            int clientID = 0;
                            if(resSet.next()) {
                                clientID = resSet.getInt(1);
                            }
                            Client client= new Client(clientID);
                            Employee employee = new Employee(DeliveryService.getInstance());
                            break;
                    }
                    frame.dispose();
                }else{
                    checkCredentialsText.setVisible(true);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ConnectionFactory.close(connection);
        }
    }

    private class RegisterButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            frame.setVisible(false);
            Register.getInstance().setVisible(true);
        }
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}

