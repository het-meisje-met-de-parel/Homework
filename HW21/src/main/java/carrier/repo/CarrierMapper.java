package carrier.repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import carrier.domain.Carrier;
import carrier.domain.CarrierType;

public class CarrierMapper {

    public static Carrier fromResultSet (ResultSet rs) throws SQLException {
        final Carrier carrier = new Carrier ();
        
        carrier.setAddress (rs.getString ("address"));
        carrier.setName (rs.getString ("name"));
        carrier.setId (rs.getLong ("id"));
        
        try {            
            CarrierType type = CarrierType.valueOf (rs.getString ("carrier_type"));
            carrier.setCarrierType (type);
        } catch (IllegalArgumentException iae) {
            
        }
        
        return carrier;
    }

    public static void initInsertStatement (PreparedStatement statement, Carrier entity) throws SQLException {
        statement.setLong  (1, entity.getId ());
        statement.setString(2, entity.getName ());
        statement.setString(3, entity.getAddress ());
        statement.setString(4, entity.getCarrierType ().name ());
    }

    public static void initUpdateStatement (PreparedStatement statement, Carrier entity) throws SQLException {
        statement.setLong  (4, entity.getId ());
        
        statement.setString(1, entity.getName ());
        statement.setString(2, entity.getAddress ());
        statement.setString(3, entity.getCarrierType ().name ());
    }
    
}
