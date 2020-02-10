package application.db;

import java.sql.SQLException;

public interface EDBConsumer <T> {
    
    void consume (T value, int param) throws SQLException;
    
}
