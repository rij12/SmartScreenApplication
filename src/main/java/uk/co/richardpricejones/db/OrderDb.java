package uk.co.richardpricejones.db;

import uk.co.richardpricejones.models.Order;

import javax.xml.crypto.Data;
import java.sql.*;

public class OrderDb {

    private static OrderDb instance = null;
    private static final String url = "jdbc:sqlite:SQLite.db";

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
    // todo - change "order" ORDER as it says on the requirements specification.
    public void insert(Order order) throws ClassNotFoundException {

        String sql = "INSERT INTO orders(Order_ID,Order_Number,Person_ID) VALUES(" + order.getId() + ", " + order.getOrderNumber() + "," + order.getPersonId() + ")";

        Connection conn = null;
        try {
            // Create a Connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            // Use Connection - Insert
            PreparedStatement pstmt = conn.prepareStatement(sql);

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
        return null; // magic
    }

    public void update(Order order) {

    }

    public void delete(Long id) {
        // lol
    }


    // todo - change "order" ORDER as it says on the requirements specification.
    public void CreateOrderTable(String tableName) {

        String sqlCreate = "CREATE TABLE IF NOT EXISTS orders (\n"
                + "	Order_ID integer PRIMARY KEY,\n"
                + "	Order_Number integer,\n"
                + "	Person_ID integer NOT NULL\n"
                + ");";

        Connection conn = null;
        try {

            // Create a Connection
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            // Use the connection
            Statement stmt = conn.createStatement();
            stmt.execute(sqlCreate);


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


}


