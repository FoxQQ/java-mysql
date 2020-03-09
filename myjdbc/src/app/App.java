//run with
//java -cp ./bin/lib/mysql-connector-java-8.0.19.jar:./bin/ app.App

package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("got it :)");
        } catch (Exception ex) {
            System.out.println("not found :(");
        }

        Connection conn = null;
        try {
            System.out.println("connecting...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/EMP?" + "user=root&password=1234");
            System.out.println("...connected");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            // rs = stmt.executeQuery("SELECT * FROM Employees");

            if (stmt.execute("SELECT * FROM Employees")) {
                rs = stmt.getResultSet();

            }
            int results = 0;
            int columns = 0;
            if (rs != null) {
                columns = rs.getMetaData().getColumnCount();
                System.out.println("Table has x columns:" + columns);
                for (int i = 1; i <= columns; i++) {
                    System.out
                            .println(rs.getMetaData().getColumnClassName(i) + ":" + rs.getMetaData().getColumnName(i));
                }
            }

            while (rs.next()) {
                results++;
                System.out.println(rs.getRow()); // returns row index
                for (int i = 1; i <= columns; i++) {
                    System.out.println(rs.getString(i));
                }

            }
            System.out.println(results);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                }
                stmt = null;
            }
        }
    }
};