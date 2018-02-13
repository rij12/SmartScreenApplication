package uk.co.richardpricejones.main;

import uk.co.richardpricejones.db.OrderDb;
import uk.co.richardpricejones.db.PersonDb;
import uk.co.richardpricejones.models.Order;
import uk.co.richardpricejones.models.Person;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    // Config File start.
    public static final String JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLite.db";
    public static final String JDBC_CLASS_FOR = "org.sqlite.JDBC";
    // Config File end.

    private void run(String[] args) {

        OrderDb orderDb = OrderDb.getInstance();
        PersonDb personDb = PersonDb.getInstance();

        //List the Program arguments

        for (String arg: args) {
            System.out.println(arg);
        }
        System.out.println("");

        List<String> orders = readFile(args[0]);
        List<String> persons = readFile(args[1]);

        // Create order Table
        orderDb.createOrderTable();

        // Create Person table
        personDb.createPersonTable();

        // Create Order Objects from input data
        // Skip is used to NOT process the first line, due it being the table headings.
        orders.stream().skip(1).forEach(order -> {
            String orderArgs[] = order.split("\\|");

            if (orderArgs.length != 3) {
                System.err.println("Incorrect Number of order Arguments!");
                System.exit(1);
            }

            // Create order Object,  ID | OrderNumber | PersonID
            Order orderobj = new Order(Long.parseLong(orderArgs[0]), Long.parseLong(orderArgs[1]), Long.parseLong(orderArgs[2]));

            // Add Order to Database.
            try {
                orderDb.insert(orderobj);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't insert into the Order table " + e);
            }
        });

        // Create Person Table and Person Objects
        // Again skip the first line because it describes the table headings.
        persons.stream().skip(1).forEach(person -> {
            String personArgs [] = person.split(",");

            if(personArgs.length == 5){
                //Create Person objects from file data
                Person personobj = new Person(Long.parseLong(personArgs[0]),personArgs[1],personArgs[2],personArgs[3],personArgs[4]);

                try {
                    personDb.insert(personobj);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // Interview Requirements

        // 1. Person with at least one order!
        personDb.personWithAtLeastOneOrder();

        // 2. All Orders with First Name of the corresponding person (if available)
        orderDb.findAllOrdersWithFirstName();

    }

    /**
     * Read input from a text file.
     *
     * @param fileName - name of provided text file in program args
     * @return
     */
    private static List<String> readFile(String fileName) {
        // Contains all available
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
        } catch (IOException ioe) {
            System.err.print("Error Parsing file, is it the correct provide the correct format?");
            System.exit(1);
        }
        return lines;
    }

    public static void main(String[] args){
        new Main().run(args);
    }
}
