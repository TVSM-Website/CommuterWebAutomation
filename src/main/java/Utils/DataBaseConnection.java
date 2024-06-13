package Utils;

import java.sql.*;

public class DataBaseConnection
{

    public static String server = "tvsmazwebdbsuat01.database.windows.net";
    public static String database = "tvsmazwebsdbuat01-corp-transaction";
    public static String username = "sqladmin";
    public static String password = "BFwVqmKq2D6NwGUmjjV3fExQPreProd";
    // Method to establish database connection
    public static Connection getConnection(String server, String database, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Create connection URL
        String conUrl = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + username + ";password=" + password;

        // Establish database connection
        return DriverManager.getConnection(conUrl);
    }

    // Method to execute a query and return the ResultSet
    public static ResultSet executeQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    // Method to close connection, statement, and result set
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
