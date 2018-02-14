package uk.co.richardpricejones.db;

import org.junit.*;
import uk.co.richardpricejones.models.Order;
import uk.co.richardpricejones.models.Person;


import java.io.File;

public class OrderDbTest {

    @BeforeClass
    public static void setup() {
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

    @AfterClass
    public static void teardown() {
        deleteTestDb();
    }

    @Test
    public void findAllOrdersWithFirstName() throws ClassNotFoundException {
        createDummyData();
        String testString = "Order Number: " + "2000" + ", " + "OrderId: " + "10" + ", " + "First Name: " + "Richard";
        Assert.assertTrue(OrderDb.getInstance().findAllOrdersWithFirstName().get(0).equals(testString));
    }


    /**
     * Create Dummy Data for the Find all order with first Name method.
     *
     * Steps:
     *  1. Create a person that have an Order.
     *  2. Create two Orders one with and another without a person.
     */
    private static void createDummyData() throws ClassNotFoundException {
        OrderDb.getInstance().insert(new Order(10, 2000,1));
        PersonDb.getInstance().insert(new Person(1, "Richard", "Price-Jones", "32 High St",
                "London"));

    }

    private static void deleteTestDb() {
        File testDb = new File("SQLiteTest.db");
        if (testDb.exists()) {
            testDb.delete();
        }
    }

}
