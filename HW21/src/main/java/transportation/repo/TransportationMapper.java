package transportation.repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import cargo.repo.CargoRepo;
import carrier.repo.CarrierRepo;
import transportation.domain.Transportation;

public class TransportationMapper {

    public static Transportation fromResultSet (ResultSet rs, 
            CargoRepo cargoRepo, CarrierRepo carrierRepo
    ) throws SQLException {
        final Transportation transportation = new Transportation ();
        
        String date = rs.getString("begin_date");
        date = date == null ? null : date.substring(0, "xxxx-xx-xx".length());
        transportation.setTransportationBeginDate (date == null ? null : LocalDate.parse(date));
        
        transportation.setDescription (rs.getString ("description"));
        transportation.setBillTo (rs.getString ("bill_to"));
        transportation.setId (rs.getLong ("id"));
        
        transportation.setCarrier (carrierRepo.findById (rs.getLong ("carrier")).get ());
        transportation.setCargo (cargoRepo.findById (rs.getLong ("cargo")).get ());
        
        return transportation;
    }

    public static void initInsertStatement (PreparedStatement statement, 
            Transportation entity) throws SQLException {
        statement.setLong  (1, entity.getId ());
        statement.setLong  (2, entity.getCargo ().getId ());
        statement.setLong  (3, entity.getCarrier ().getId ());
        statement.setString(4, entity.getDescription ());
        statement.setString(5, entity.getBillTo ());
        statement.setString(6, entity.getTransportationBeginDate ().toString () + " 00:00:00");
    }

    public static void initUpdateStatement (PreparedStatement statement, 
            Transportation entity) throws SQLException {
        statement.setLong  (6, entity.getId ());
        
        statement.setLong  (1, entity.getCargo ().getId ());
        statement.setLong  (2, entity.getCarrier ().getId ());
        statement.setString(3, entity.getDescription ());
        statement.setString(4, entity.getBillTo ());
        statement.setString(5, entity.getTransportationBeginDate ().toString () + " 00:00:00");
    }
    
}
