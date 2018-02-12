package uk.co.richardpricejones.db;

import uk.co.richardpricejones.models.Person;

import java.sql.*;

public class PersonDb {


    private static PersonDb instance = null;
    private static final String JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLite.db";

    private PersonDb() {
        // empty
    }

    public static PersonDb getInstance() {
        if (instance == null)
            instance = new PersonDb();
        return instance;
    }


    /**
     * @param person
     * @throws ClassNotFoundException
     */
    public void insert(Person person) throws ClassNotFoundException {
        String sql = "INSERT INTO ORDER(Order_ID,Order_Number,Person_ID) VALUES(" + person.getId() + ", " + person.getFirstName() + "," + person.getLastName() + "," + person.getStreet() + " ," + person.getCity() + ")";

        Connection conn = null;
        try {
            // Create a Connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(JDBC_SQL_LITE_FILENAME);

            // Use Connection - Insert Person
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Execute the update.
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Couldn't update person Table" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Couldn't close the connection when inserting into the person table" + e);
            }
        }
    }

    public Person select(Long id) {
        return null; // magic
    }

    public void update(Person person) {

    }

    public void delete(Long id) {
        // lol
    }


    public void CreatePersonTable() {

        String sqlCreate = "CREATE TABLE IF NOT EXISTS PERSON (\n"
                + "	Person_ID integer PRIMARY KEY,\n"
                + "	First_Name text,\n"
                + "	Last_Name text, \n"
                + "	Street text, \n"
                + "	City text, \n"
                + ");";

        Connection conn = null;
        try {

            // Create a Connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(JDBC_SQL_LITE_FILENAME);

            // Use Connection - Create a PERSON table
            Statement stmt = conn.createStatement();
            stmt.execute(sqlCreate);


        } catch (Exception e) {
            System.err.println("Could not create Person Table" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
