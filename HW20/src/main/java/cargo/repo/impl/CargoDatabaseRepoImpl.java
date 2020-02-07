package cargo.repo.impl;

import static application.db.DBConnectionProducer.*;
import static application.db.DBQueries.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import cargo.domain.*;
import cargo.search.CargoSearchCondition;
import common.solutions.utils.CollectionUtils;
import storage.IdGenerator;

public class CargoDatabaseRepoImpl extends CommonCargoRepo{

    public CargoDatabaseRepoImpl () {
        prepareStatement (DELETE_ALL_CARGO).ifPresent (statement -> {
            try {
                statement.executeUpdate ();
            } catch (SQLException sqle) {
                throw new RuntimeException (sqle);
            }
        });
    }
    
    @Override
    public Optional<Cargo> getByIdFetchingTransportations(long id) {
        return findById (id);
    }

    @Override
    public Cargo[] findByName(String name) {
        return prepareStatement (SELECT_CARGOS_BY_NAME).map(statement -> {
            try {
                statement.setString (1, name);
                
                final var result = statement.executeQuery();
                List <Cargo> cargos = new ArrayList<> ();
                while (result.next()) {
                    cargos.add(convertToCargo(result));
                }

                return cargos;
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return null;
            }
        }).orElse(List.of ()).toArray (Cargo []::new);
    }

    @Override
    public List<Cargo> search(CargoSearchCondition searchCondition) {
        List<Cargo> cargos = getAll();

        if (CollectionUtils.isNotEmpty(cargos)) {
          if (searchCondition.needSorting()) {
            Comparator<Cargo> cargoComparator = createCargoComparator(searchCondition);
            cargos.sort(searchCondition.isAscOrdering() ? cargoComparator : cargoComparator.reversed());
          }
        }

        return cargos;
    }

    @Override
    public Optional<Cargo> findById(Long aLong) {
        return prepareStatement (SELECT_CARGO_BY_ID).map(statement -> {
            try {
                statement.setLong(1, aLong);

                var result = statement.executeQuery();
                if (result.next ()) {                    
                    return convertToCargo(result);
                } else {
                    return null;
                }
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return null;
            }
        });
    }

    @Override
    public void save(Cargo entity) {
        if (entity == null) { return; }
        if (entity.getId () == null) {
            entity.setId (IdGenerator.generateId ());
        }
        
        prepareStatement (INSERT_CARGO).ifPresent(statement -> {
            try {
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

                statement.executeUpdate ();
            } catch (SQLException sqle) {
                System.err.println (sqle);
            }
        });
    }

    @Override
    public boolean update(Cargo entity) {
        if (entity == null || entity.getId () == null) { 
            return false; 
        }
        
        return prepareStatement (UPDATE_CARGO).map(statement -> {
            try {
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

                statement.executeUpdate ();
                return true;
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return false;
            }
        }).orElse (false);
    }

    @Override
    public boolean deleteById(Long aLong) {
        if (aLong == null) { return false; }
        
        return prepareStatement (DELETE_CARGO_BY_ID).map(statement -> {
            try {
                statement.setLong(1, aLong);
                statement.executeUpdate ();
                return true;
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return false;
            }
        }).orElse (false);
    }

    @Override
    public List<Cargo> getAll() {
        return prepareStatement (SELECT_ALL_CARGO).map(statement -> {
            try {
                final var result = statement.executeQuery();
                List <Cargo> cargos = new ArrayList<> ();
                while (result.next()) {
                    cargos.add(convertToCargo(result));
                }

                return cargos;
            } catch (SQLException sqle) {
                System.err.println (sqle);
                return null;
            }
        }).orElse(List.of ());
    }

    @Override
    public int countAll() {
        return prepareStatement (COUNT_CARGOS).map(statement -> {
            try {
                var result = statement.executeQuery();
                result.next();

                return result.getInt(1);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                return null;
            }
        }).orElse(0);
    }

    private static Cargo convertToCargo (ResultSet result) throws SQLException {
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

}
