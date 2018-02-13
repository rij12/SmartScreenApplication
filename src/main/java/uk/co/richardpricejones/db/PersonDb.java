package uk.co.richardpricejones.db;

import uk.co.richardpricejones.main.Main;
import uk.co.richardpricejones.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDb {


    private static PersonDb instance = null;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS PERSON (\n"
            + "	Person_ID INTEGER PRIMARY KEY,\n"
            + "	First_Name TEXT,\n"
            + "	Last_Name TEXT, \n"
            + "	Street TEXT, \n"
            + "	City TEXT \n"
            + ");";

    private static final String INSERT_PERSON = "INSERT INTO PERSON (Person_ID, First_Name, Last_Name, Street, City) "
            + "VALUES(?, ?, ?, ?, ?)";

    private static final String PERSON_WITH_AT_LEAST_ONE_ORDER = "SELECT Person.Person_ID FROM PERSON INNER JOIN 'ORDER' ON Person.Person_ID = 'ORDER'.Person_ID";

    private static final String FIND_PERSON_BY_ID = "SELECT * FROM PERSON WHERE PERSON_ID = ?";

    private static final String SELECT_ALL_ORDERS = "SELECT * FROM PERSON";

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
            conn = DatabaseDriverManager.getConnection();

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

    public void update(Person person) {

    }

    public void delete(Long id) {

    }

    /**
     * Get Person record via a Person_ID(long)
     *
     * @param id Person_Id
     * @return Populated Person Object.
     */
    public Person selectById(int id) {

        Connection conn = null;

        Person person;
        try {
            // Create a Connection.
            conn = DatabaseDriverManager.getConnection();

            // Use Connection - Create find a Person record via a given Person_ID.
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
            System.err.println("Couldn't find Person with ID " + id + ", " + e);
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
     * Create a person Table with the Require schema.
     */
    public void createPersonTable() {
        Connection conn = null;
        try {
            // Create a Connection
            conn = DatabaseDriverManager.getConnection();

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

    /**
     * Get A person with a least one Order.
     *
     * @return List a People that have at least one order.
     */
    public List<Person> personWithAtLeastOneOrder() {
        ArrayList<Integer> personWithAtLeastOneOrder = new ArrayList<>(5);
        List<Person> people = new ArrayList<>();

        Connection conn = null;
        try {
            // Create a Connection
            conn = DatabaseDriverManager.getConnection();

            // Use Connection - Create a PERSON table
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(PERSON_WITH_AT_LEAST_ONE_ORDER);
            // List of ID's of people who have at least one order.
            while (rs.next()) {
                Integer personId = Integer.parseInt(rs.getString("Person_ID"));
                // Add id to array if not already in there.

                // Check if the Person_ID is already in the arrayList.
                if (!personWithAtLeastOneOrder.contains(personId)) {
                    personWithAtLeastOneOrder.add(personId);
                }
            }

            // Return Array of ID's and then use this to find the the people from the people column.
            personWithAtLeastOneOrder.forEach(p -> {
                Person person = selectById(p);
                if (person != null) people.add(person);
            });

            System.out.println("Persons with at least one Order");
            people.forEach(p -> System.out.println(p));
            System.out.println("");
        } catch (Exception e) {
            System.err.println("Couldn't find execute Person with at least one order SQL statement!" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return people;
    }

    public List<Person> selectAll() throws ClassNotFoundException {
        List<Person> people = new ArrayList<>();

        Connection conn = null;

        try {
            // Create a Connection.
            conn = DatabaseDriverManager.getConnection();

            // Use Connection - Select all from ORDERS Table.
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_ORDERS);
            // List of ID's of people who have at least one order.
            while (rs.next()) {
                long person_id = rs.getLong("Person_ID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String street = rs.getString("Street");
                String city = rs.getString("City");

                Person person = new Person(person_id, firstName, lastName, street, city);
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return people;
    }
}
