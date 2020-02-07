package application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

// class is not thread-safe
public class DBConnectionProducer {

    private static String login = "sandbox";
    private static String password = "sandbox";
    private static String database = "epam-transport";
    private static String url = "jdbc:mysql://localhost/" + database + "?useUnicode=true&serverTimezone=UTC";

    private static Optional<Connection> transactionConnection = Optional.empty ();
    
    public static Optional<Connection> openConnection() {
        if (transactionConnection.isPresent ()) {
            return transactionConnection;
        }
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException sqle) {
            System.err.println (sqle);
            return null;
        }

        return Optional.ofNullable(connection);
    }
    
    public static Optional<Connection> openTransaction () {
        if (transactionConnection.isPresent ()) {
            throw new IllegalStateException ("Transaction connection is already opened");
        }
        
        transactionConnection = openConnection ().map (connection -> {
            try {
                connection.setAutoCommit (false);
                return connection;
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return null;
            }
        });
        
        return transactionConnection;
    }
    
    public static void closeTransaction () {
        if (transactionConnection.isEmpty ()) {
            throw new IllegalStateException ("Transaction connection is not opened");
        }
        
        transactionConnection.ifPresent (connection -> {
            try {
                connection.commit ();
                connection.close ();
            } catch (SQLException sqle) {
                System.err.println (sqle);
            }
        });
        
        transactionConnection = Optional.empty ();
    }
    
    public static Optional<PreparedStatement> prepareStatement (String query) {
        return openConnection ().map (con -> {
            try {
                return con.prepareStatement (query);
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return null;
            }
        });
    }

}
