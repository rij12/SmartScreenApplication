package uk.co.richardpricejones.db;

import uk.co.richardpricejones.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDb {

    private static OrderDb instance = null;

    private static final String INSERT_ORDER = "INSERT INTO 'ORDER'(ORDER_ID,ORDER_NO,PERSON_ID)"
            + "VALUES(?, ?, ?)";

    private static final String ALL_ORDERS_WITH_FIRST_NAME = "SELECT 'ORDER'.ORDER_NO, 'Order'.ORDER_ID , " +
            "PERSON.FIRST_NAME FROM 'ORDER' LEFT JOIN PERSON " +
            "ON 'ORDER'.PERSON_ID = PERSON.PERSON_ID";

    private static final String ORDER_TABLE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS 'ORDER' (\n"
            + "	ORDER_ID INTEGER PRIMARY KEY,\n"
            + "	ORDER_NO INTEGER,\n"
            + "	PERSON_ID INTEGER \n"
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
     *
     * @param order order object to be inserted into the ORDER table.
     */
    public void insert(Order order) throws ClassNotFoundException {

        Connection conn = null;
        try {
            conn = DatabaseDriverManager.getConnection();
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

    public void createOrderTable() {

        Connection conn = null;
        try {
            // Create a Connection
            conn = DatabaseDriverManager.getConnection();

            // Use the connection
            Statement stmt = conn.createStatement();
            stmt.execute(ORDER_TABLE_CREATE_SQL);

        } catch (Exception e) {
            System.err.println("Could not create ORDER Table" + e);
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

    public List<String> findAllOrdersWithFirstName() {
        Connection conn = null;

        List<String> ordersWithFirstNames = new ArrayList<>();
        try {
            // Create a Connection
            conn = DatabaseDriverManager.getConnection();

            // Use the connection
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_ORDERS_WITH_FIRST_NAME);

            // Only returning a List of strings because that fulfils the requirement.
            while (rs.next()) {
                long orderId = rs.getLong("ORDER_ID");
                long orderNumber = rs.getLong("ORDER_NO");
                String firstName = rs.getString("FIRST_NAME");
                String orderWithFirstName = "Order Number: " + orderNumber + ", " + "OrderId: " + orderId + ", " +
                        "First Name: " + firstName;
                ordersWithFirstNames.add(orderWithFirstName);
            }
            return ordersWithFirstNames;
        } catch (Exception e) {
            System.err.println("Could not get orders with First Names: " + e);
            return ordersWithFirstNames;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Couldn't close Database Connection see error: " + ex.getMessage());
            }
        }
    }
}



