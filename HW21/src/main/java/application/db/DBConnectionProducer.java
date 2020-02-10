package application.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.solutions.utils.Tup4;

public class DBConnectionProducer {
    
    private static final String URL = "jdbc:mysql://localhost/epam_transport?useUnicode=true&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";
    
    public static void query (String query, boolean modifying,
        EDBConsumer <PreparedStatement> initializer,
        EDBConsumer <ResultSet> consumer
    ) throws SQLException {
        queries (false, Tup4.of (query, modifying, initializer, consumer));
    }
    
    @SafeVarargs
    public static void queries (
        boolean transactional, 
        Tup4 <String, Boolean, EDBConsumer <PreparedStatement>, EDBConsumer <ResultSet>>... tasks
    ) throws SQLException {
        try (
            var connection = DriverManager.getConnection (URL, LOGIN, PASSWORD);
        ) {
            connection.setAutoCommit (!transactional);
            
            for (var task : tasks) {
                var statement = connection.prepareStatement (task.T1);
                task.T3.consume (statement, 0);
                
                if (task.T2) {
                    task.T4.consume (null, statement.executeUpdate ());
                } else {
                    var rs = statement.executeQuery ();
                    while (rs.next ()) {                        
                        task.T4.consume (rs, -1);
                    }
                }
            }
            
            if (transactional) { connection.commit (); }
        }
    }
    
}
