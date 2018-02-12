package uk.co.richardpricejones.db;

import uk.co.richardpricejones.models.Person;

import java.sql.*;

public class PersonDb {


    private static PersonDb instance = null;
    private static final String JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLite.db";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PERSON (\n"
            + "	Person_ID integer PRIMARY KEY,\n"
            + "	First_Name text,\n"
            + "	Last_Name text, \n"
            + "	Street text, \n"
            + "	City text \n"
            + ");";

    private static final String INSERT_PERSON = "INSERT INTO PERSON (Person_ID, First_Name, Last_Name, Street, City) "
            + "VALUES(?, ?, ?, ?, ?)";

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
        Connection conn = null;
        try {
            // Create a Connection.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(JDBC_SQL_LITE_FILENAME);

            // Use Connection - Insert Person.
            PreparedStatement pstmt = conn.prepareStatement(INSERT_PERSON);
            pstmt.setLong(1, person.getId());
            pstmt.setString(2, person.getFirstName());
            pstmt.setString(3, person.getLastName());
            pstmt.setString(4, person.getStreet());
            pstmt.setString(5, person.getCity());

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

        Connection conn = null;
        try {

            // Create a Connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(JDBC_SQL_LITE_FILENAME);

            // Use Connection - Create a PERSON table
            Statement stmt = conn.createStatement();
            stmt.execute(CREATE_TABLE);
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
