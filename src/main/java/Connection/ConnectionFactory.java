package Connection;

import java.sql.*;
import java.util.logging.Logger;

/**Factory class used to establish connection to the database*/
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/food_delivery";
    private static final String USER = "root";
    private static final String PASS = "admin";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    private static Connection connection;

    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**Creates connections*/
    private Connection createConnection(){
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
            return connection;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return singleInstance.createConnection();
    }

    public static void close(Connection connection){
        if(connection == null)
            return;

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Closes connection*/
    public static void close(Statement statement){
        if(statement == null)
            return;

        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet){
        if(resultSet == null)
            return;

        try {
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}