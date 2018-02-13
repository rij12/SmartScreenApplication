package uk.co.richardpricejones.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.richardpricejones.main.Main;
import uk.co.richardpricejones.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDb {

    /**
     * Query used for testing
     */

    // Config File start.
    private static final String JDBC_SQL_LITE_FILENAME_TEST = "jdbc:sqlite:SQLiteTest.db";
    private static final String JDBC_CLASS_FOR = "org.sqlite.JDBC";
    // Config File end.

    //    private static final String = "";
    private static final String PERSON_WITH_AT_LEAST_ONE_ORDER = "SELECT Person.Person_ID FROM PERSON INNER JOIN 'ORDER' ON Person.Person_ID = 'ORDER'.Person_ID";
    private static final String FIND_PERSON_BY_ID = "SELECT * FROM PERSON WHERE PERSON_ID = ?";
    private Connection conn = null;

    //Person Object to compared to.
    private static final Person personTestCase1 = new Person(1, "Ola", "Hansen", "Timoteivn", "Sandnes");
    private static final Person personTestCase2 = new Person(2, "Tove", "Svendson", "Borgvn","Stavanger");
    private static final int TEST_SELECT_ID = 1;

    private static List<Person> testPersonList = new ArrayList<>(2);


    /**
     * Prerequisite for the tests.
     */

    @BeforeClass
    public static void setupClass(){
        testPersonList.add(personTestCase1);
        testPersonList.add(personTestCase2);
    }


    /**
     * Setup before each test
     */
    @Before
    public void setup(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Compares the lists from expected people and actual people.
     */
    @Test
    public void personWithAtLeastOneOrder(){
            List<Person> PersonWithAtLeastOneOrderList = personWithAtLeastOneOrderTestMethod();
            Assert.assertNotSame(PersonWithAtLeastOneOrderList.get(0), testPersonList.get(0));
            Assert.assertNotSame(PersonWithAtLeastOneOrderList.get(1), testPersonList.get(1));
    }

    @Test
    public void testSelectByID (){
        Person person = selectById(TEST_SELECT_ID);
        Assert.assertEquals(personTestCase1, person);
    }

    // Helper Methods //

    /**
     * Test Select by Id, Given a test database.
     */
    private Person selectById(int id){


        Person person;
        try {

            // Use Connection - Create find a Person record via a given Person_ID.
            if(conn == null){
                getConnection();
            }

            PreparedStatement pstmt = conn.prepareStatement(FIND_PERSON_BY_ID);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            // Create Person Object to be returned.
            long person_id = rs.getLong("Person_ID");
            String firstName = rs.getString("First_Name");
            String lastName = rs.getString("Last_Name");
            String street = rs.getString("Street");
            String city = rs.getString("City");

            person = new Person(person_id, firstName, lastName, street, city);

            return person;


        } catch (Exception e) {
            System.err.println("JUnit Tests: Couldn't find Person with ID " + id + ", " + e);
            return null;
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

    /**
     * Get a database connection for the JUnit Tests.
     * @return Connection conn
     */
    private Connection getConnection(){
        // Create a Connection.
        try {
            Class.forName(Main.JDBC_CLASS_FOR);
            conn = DriverManager.getConnection(JDBC_SQL_LITE_FILENAME_TEST);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Couldn't get test database connection " + e);
            return null;
        }
    }


    /**
     * Get A person with a least one Order.
     *
     * @return List a People that have at least one order.
     */
    private List<Person> personWithAtLeastOneOrderTestMethod() {

        ArrayList<Integer> personWithAtLeastOneOrder = new ArrayList<>(5);
        List<Person> people = new ArrayList<>();


        try {
            // Create a Connection
            Class.forName(Main.JDBC_CLASS_FOR);
            conn = DriverManager.getConnection(JDBC_SQL_LITE_FILENAME_TEST);

            // Use Connection - Create a PERSON table
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(PERSON_WITH_AT_LEAST_ONE_ORDER);

            // List of ID's of people who have at least one order.
            while (rs.next()) {
                Integer personId = Integer.parseInt(rs.getString("Person_ID"));
                System.out.println(personId);
                // Add id to array if not already in there.

                // Check if the Person_ID is already in the arrayList.
                if (!personWithAtLeastOneOrder.contains(personId)) {
                    personWithAtLeastOneOrder.add(personId);
                }
            }

            // Return Array of ID's and then use this to find the the people from the people column.
            personWithAtLeastOneOrder.forEach(p ->{
                System.out.println("ID " + p);
                Person person = selectById(p);
                if (person != null) people.add(person);
            });

            System.out.println("Persons with at least one Order Test ");
            people.forEach(p -> System.out.println(p));
            System.out.println("");



        } catch (Exception e) {
            System.err.println("JUnit Test: Couldn't find execute Person with at least one order SQL statement!" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

}
