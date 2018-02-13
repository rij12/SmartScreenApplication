package uk.co.richardpricejones.main;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class Main {


    /**
     * Query used for testing
     */

    // Config File start.
    public static final String JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLiteTest.db";
    public static final String JDBC_CLASS_FOR = "org.sqlite.JDBC";
    // Config File end.

    //    private static final String = "";
    private static final String PERSON_WITH_AT_LEAST_ONE_ORDER = "SELECT Person.Person_ID FROM PERSON INNER JOIN 'ORDER' ON Person.Person_ID = 'ORDER'.Person_ID";


    /**
     * Prerequisite for each test.
     */


    /**
     * Setup before each test
     */
    @Before
    public void setup(){

    }


    /**
     * Setup before class test, create a database and populate it.
     */
    @BeforeClass
    public void classSetup(){




    }





    /**
     * Find all the orders and the first name of their corresponding person.
     */
    @Test
    public void findAllOrdersWithFirstName(){

    }


    /**
     * Test Select by Id, Given a test database.
     */

    @Test
    public void selectById(Integer id){


    }





}
