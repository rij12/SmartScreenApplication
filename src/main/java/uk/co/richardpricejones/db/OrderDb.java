package uk.co.richardpricejones.db;

import uk.co.richardpricejones.main.Main;
import uk.co.richardpricejones.models.Order;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDb {

    private static OrderDb instance = null;
    private static final String url = "jdbc:sqlite:SQLite.db";

    private static final String INSERT_ORDER = "INSERT INTO 'ORDER'(Order_ID,Order_Number,Person_ID)"
                                             + "VALUES(?, ?, ?)";


    // todo Explain what this does!
    private static final String ALL_ORDERS_WITH_FIRST_NAME = "SELECT 'ORDER'.Order_Number, 'Order'.Order_ID, " +
                                                             "PERSON.First_Name FROM 'ORDER' LEFT JOIN PERSON " +
                                                             "ON 'ORDER'.Person_ID = PERSON.Person_ID";


    private static final String ORDER_TABLE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS 'ORDER' (\n"
            + "	Order_ID integer PRIMARY KEY,\n"
            + "	Order_Number integer,\n"
            + "	Person_ID integer \n"
            + ");";

    private OrderDb() {
        // empty
    }

    public static OrderDb getInstance() {
        if (instance == null)
            instance = new OrderDb();
        return instance;
    }


    /**
     * Given a order model object it will make an entry into the database.
     * "INSERT INTO Customers " + "VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)");
     *
     * @param order
     */
    public void insert(Order order) throws ClassNotFoundException {

        Connection conn = null;
        try {
            // Create a Connection.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            // Use Connection - Insert Order.
            PreparedStatement pstmt = conn.prepareStatement(INSERT_ORDER);

            pstmt.setLong(1, order.getId());
            pstmt.setLong(2, order.getOrderNumber());
            pstmt.setLong(3, order.getPersonId());

            // Execute the update.
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Couldn't update orders Table" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Couldn't close the connection when inserting into the order table" + e);
            }
        }
    }

    public Order select(Long id) {
        return null;
    }

    public void update(Order order) {

    }

    public void delete(Long id) {

    }

    public void CreateOrderTable() {

        Connection conn = null;
        try {

            // Create a Connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            // Use the connection
            Statement stmt = conn.createStatement();
            stmt.execute(ORDER_TABLE_CREATE_SQL);

        } catch (Exception e) {
            System.err.println("Could not create Order Table" + e);
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

    // Use Connection - Create find a Person record via a given Person_ID.
//    PreparedStatement pstmt = conn.prepareStatement(FIND_PERSON_BY_ID);
//            pstmt.setInt(1, id);
//
//
//    ResultSet rs = pstmt.executeQuery();
    // ALL_ORDERS_WITH_FIRST_NAME
    public void findAllOrdersWithFirstName(){

        Connection conn = null;

        try {

            // Create a Connection
            Class.forName(Main.JDBC_CLASS_FOR);
            conn = DriverManager.getConnection(Main.JDBC_SQL_LITE_FILENAME);

            // Use the connection
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_ORDERS_WITH_FIRST_NAME);


            System.out.println("All Orders with First Name of the corresponding person ");
            while(rs.next()){
               long  orderNumber =  rs.getLong("Order_Number");
               long  orderId =  rs.getLong("Order_ID");
               String firstName = rs.getString("First_Name");

                System.out.println("Order Number: " + orderNumber + ", "+ "OrderId: " + orderId + ", " + "First Name: " + firstName );
            }

        } catch (Exception e) {
            System.err.println("Could not get All order with First Names, " + e);

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



