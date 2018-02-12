package uk.co.richardpricejones.main;

import uk.co.richardpricejones.db.DatabaseDriverManager;
import uk.co.richardpricejones.db.OrderDb;
import uk.co.richardpricejones.db.PersonDb;
import uk.co.richardpricejones.models.Order;
import uk.co.richardpricejones.models.Person;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public class Main {
    public  static final String JDBC_SQL_LITE_FILENAME = "jdbc:sqlite:SQLite.db";

    private void run(String[] args) {

        OrderDb orderDb = OrderDb.getInstance();
        PersonDb personDb = PersonDb.getInstance();

        //List the Program arguments

        for (String arg: args) {
            System.out.println(arg);
        }


        List<String> orders = readFile(args[0]);
        List<String> persons = readFile(args[1]);




        // Create order Table
        orderDb.CreateOrderTable("ORDER");

        // Create Person table
        personDb.CreatePersonTable();


        // Create Order Objects from input data
        // Skip is used to NOT process the first line, due it being the table headings.
        orders.stream().skip(1).forEach(order -> {
            String orderArgs [] = order.split("\\|");

            if(orderArgs.length != 3 ){
                System.err.println("Incorrect Number of order Arguments!");
                System.exit(1);
            }

            // Create order Object,  ID | OrderNumber | PersonID
            Order orderobj = new Order (Long.parseLong(orderArgs[0]), Long.parseLong(orderArgs[1]), Long.parseLong(orderArgs[2]));

            // Add Order to Database.
            try {
                orderDb.insert(orderobj);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't insert into the Order table " + e);
            }

        });
//
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





    }

    /**
     * Read input from a text file.
     *
     * @param fileName - name of provided text file in program args
     * @return report - return a list that contain the parsed information.
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
