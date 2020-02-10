package cargo.repo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;

public class CargoMapper {
    
    public static Cargo fromResultSet (ResultSet result) throws SQLException {
        String type = result.getString ("cargo_type").toUpperCase();
        Cargo cargo = null;
        if (CargoType.FOOD.name().equals(type)) {
            var tmp = new FoodCargo();

            tmp.setStoreTemperature(result.getInt("store_temperature"));
            String date = result.getString("expiration_date");
            date = date == null ? null : date.substring(0, "xxxx-xx-xx".length());
            tmp.setExpirationDate(date == null ? null : LocalDate.parse(date));

            cargo = tmp;
        } else {
            var tmp = new ClothersCargo();

            tmp.setMaterial(result.getString("material"));
            tmp.setSize(result.getString("size"));

            cargo = tmp;
        }

        cargo.setWeight(result.getInt("weight"));
        cargo.setName(result.getString("name"));
        cargo.setId(result.getLong ("id"));
        cargo.setTransportations(new ArrayList <> ());

        return cargo;
    }
    
    public static void initInsertStatement (PreparedStatement statement, Cargo entity) throws SQLException {
        statement.setLong  (1, entity.getId ());
        statement.setString(2, entity.getName ());
        statement.setInt   (3, entity.getWeight ());
        statement.setString(4, entity.getCargoType ().name ());
        
        if (CargoType.FOOD.equals (entity.getCargoType ())) {
            FoodCargo fcargo = (FoodCargo) entity;
            statement.setObject (5, null);
            statement.setObject (6, null);
            statement.setString (7, fcargo.getExpirationDate ().toString () + " 00:00:00");
            statement.setInt    (8, fcargo.getStoreTemperature ());
        } else if (CargoType.CLOTHERS.equals (entity.getCargoType ())) {
            ClothersCargo ccargo = (ClothersCargo) entity;
            statement.setString (5, ccargo.getSize ());
            statement.setString (6, ccargo.getMaterial ());
            statement.setString (7, null);
            statement.setObject (8, null);
        }
    }
    
    public static void initUpdateStatement (PreparedStatement statement, Cargo entity) throws SQLException {
        statement.setLong  (8, entity.getId ());
        
        statement.setString(1, entity.getName ());
        statement.setInt   (2, entity.getWeight ());
        statement.setString(3, entity.getCargoType ().name ());
        
        if (CargoType.FOOD.equals (entity.getCargoType ())) {
            FoodCargo fcargo = (FoodCargo) entity;
            statement.setObject (4, null);
            statement.setObject (5, null);
            statement.setString (6, fcargo.getExpirationDate ().toString () + " 00:00:00");
            statement.setInt    (7, fcargo.getStoreTemperature ());
        } else if (CargoType.CLOTHERS.equals (entity.getCargoType ())) {
            ClothersCargo ccargo = (ClothersCargo) entity;
            statement.setString (4, ccargo.getSize ());
            statement.setString (5, ccargo.getMaterial ());
            statement.setString (6, null);
            statement.setObject (7, null);
        }
    }
    
}
