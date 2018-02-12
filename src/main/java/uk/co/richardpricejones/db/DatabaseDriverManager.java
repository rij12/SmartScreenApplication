package uk.co.richardpricejones.db;

import java.sql.*;
//import java.sql.Connection;


public class DatabaseDriverManager {


    private static final String JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLite.db";
    /*
     * 1. Driver Class
     * 2. Connection URL
     */

//    private static DatabaseDriverManager instance = null;
//
//    public static DatabaseDriverManager getInstance() {
//        if (instance == null)
//            instance = new DatabaseDriverManager();
//        return instance;
//    }
//
//    private DatabaseDriverManager(){
//        // empty
//    }

    private Connection databaseConnection = null;

    public Connection connectToDatabase(String url) throws SQLException {

        /*
         * I would Normally use a property file that the Connection URL, Username and Password(Hashed)
         */
        Connection con = null;

        // Create a database Connection

        try {
            Class.forName("org.sqlite.JDBC");
            con =  DriverManager.getConnection(url);

        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return con;
    }

    public Connection getConnection() {
        if (databaseConnection != null) {
            return databaseConnection;
        } else {
            try {
                databaseConnection = connectToDatabase(JDBC_SQL_LITE_FILENAME);
                return databaseConnection;
            } catch (SQLException e) {
                System.err.println("Couldn't get database connection" + e);
            }
            return null;
        }

    }

    public void closeConnection(){
        try {
            databaseConnection.close();
        } catch (SQLException e) {
            System.err.println("Couldn't close database connection" + e);
        }
    }


}
