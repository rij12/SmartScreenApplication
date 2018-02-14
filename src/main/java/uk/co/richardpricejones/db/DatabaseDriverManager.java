package uk.co.richardpricejones.db;

import java.sql.*;

public class DatabaseDriverManager {

    private static final String JDBC_SQL_LITE_FILENAME;

    static {
        if (System.getProperty("IS_TEST") == null)
            JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLite.db";
        else
            JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLiteTest.db";
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(JDBC_SQL_LITE_FILENAME);
    }
}
