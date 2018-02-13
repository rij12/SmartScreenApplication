package uk.co.richardpricejones.db;

import org.junit.*;
import uk.co.richardpricejones.models.Order;
import uk.co.richardpricejones.models.Person;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersonDbTest {

    @Before
    public void setup() {
        /*
         * 1. Run the app in the test mode
         * 2. Delete the test db if exists
         * 3. Initiate the tables
         */
        System.setProperty("IS_TEST", "true");
        deleteTestDb();
        PersonDb.getInstance().createPersonTable();
        OrderDb.getInstance().createOrderTable();
    }

    @After
    public void teardown() {
        deleteTestDb();
    }

    @Test
    public void testInsertPerson() throws ClassNotFoundException {
        PersonDb.getInstance().insert(new Person(1, "Richard", "Price-Jones", "32 High St",
                "London"));
        Assert.assertEquals(1, PersonDb.getInstance().selectAll().size());
    }

    @Test
    public void personWithAtLeastOneOrder() throws ClassNotFoundException {

        List<Person> testPersonList = new ArrayList<>(1);
        Person p1 = new Person(2, "James", "Bond", "32 High St",
                "London");
        testPersonList.add(p1);

        CreateDummyDataForPersonWithAtLeastOneOrder();
        Assert.assertEquals(testPersonList,PersonDb.getInstance().personWithAtLeastOneOrder());
    }


    /**
     * Create Dummy data for the Person with at least one order requirement.
     *   Insert Dummy Data Steps:
     *   1. Insert 2 people, with one person that has an order.
     *   2. Insert an Order into the ORDER table that has a Person_ID that is the same as one of the insert people.
     */
    private static void CreateDummyDataForPersonWithAtLeastOneOrder() throws ClassNotFoundException {

        PersonDb.getInstance().insert(new Person(2, "James", "Bond", "32 High St",
                "London"));
        PersonDb.getInstance().insert(new Person(3, "Sam", "Smith", "32 High St",
                "London"));
        OrderDb.getInstance().insert(new Order(10,2000,2));

    }

    private static void deleteTestDb() {
        File testDb = new File("SQLiteTest.db");
        if (testDb.exists()) {
            testDb.delete();
        }
    }



}
