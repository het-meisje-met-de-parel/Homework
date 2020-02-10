package cargo.repo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import application.db.DBConnectionProducer;
import cargo.domain.Cargo;
import cargo.repo.CargoMapper;
import cargo.search.CargoSearchCondition;
import common.solutions.utils.CollectionUtils;

public class CargoDBRepoImpl extends CommonCargoRepo {
    
    @Override
    public Optional <Cargo> getByIdFetchingTransportations (long id) {
        return findById (id);
    }

    @Override
    public Cargo [] findByName (String name) {
        List <Cargo> result = new ArrayList <> ();
        
        try {
            String query = "SELECT * FROM `cargo` WHERE `name` = ?";
            DBConnectionProducer.query (query, false, (st, __) -> {
                st.setString (1, name);
            }, (rs, __) -> {
                result.add (CargoMapper.fromResultSet (rs));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return result.toArray (Cargo []::new);
    }

    @Override
    public List <Cargo> search (CargoSearchCondition searchCondition) {
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
    public Optional <Cargo> findById (Long id) {
        AtomicReference <Cargo> reference = new AtomicReference <> ();
        
        try {
            String query = "SELECT * FROM `cargo` WHERE `id` = ?";
            DBConnectionProducer.query (query, false, (st, __) -> {
                st.setLong (1, id);
            }, (rs, __) -> {
                reference.set (CargoMapper.fromResultSet (rs));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return Optional.ofNullable (reference.get ());
    }

    @Override
    public void save (Cargo entity) {
        try {
            String query = "INSERT `cargo` ( "
                         + "  `id`, `name`, `weight`, `cargo_type`, "
                         + "  `size`, `material`, `expiration_date`, "
                         + "  `store_temperature`"
                         + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            DBConnectionProducer.query (query, true, (st, __) -> {
                CargoMapper.initInsertStatement (st, entity);
            }, (__, rows) -> {
                // nothing to do
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
    }

    @Override
    public boolean update (Cargo entity) {
        AtomicBoolean reference = new AtomicBoolean ();
        
        try {
            String query = "UPDATE `cargo` SET "
                         + "  `name` = ?, "
                         + "  `weight` = ?, "
                         + "  `cargo_type` = ?, "
                         + "  `size` = ?, "
                         + "  `material` = ?, "
                         + "  `expiration_date` = ?, "
                         + "  `store_temperature` = ? "
                         + "WHERE `id` = ?";
            DBConnectionProducer.query (query, true, (st, __) -> {
                CargoMapper.initUpdateStatement (st, entity);
            }, (__, rows) -> {
                reference.set (rows == 1);
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return reference.get ();
    }

    @Override
    public boolean deleteById (Long id) {
        AtomicBoolean reference = new AtomicBoolean ();
        
        try {
            String query = "DELETE FROM `cargo` WHERE `id` = ?";
            DBConnectionProducer.query (query, true, (st, __) -> {
                st.setLong (1, id);
            }, (__, rows) -> {
                reference.set (rows == 1);
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return reference.get ();
    }

    @Override
    public List <Cargo> getAll () {
        List <Cargo> result = new ArrayList <> ();
        
        try {
            String query = "SELECT * FROM `cargo` WHERE 1";
            DBConnectionProducer.query (query, false, (st, __) -> {
                // nothing to initialize
            }, (rs, __) -> {
                result.add (CargoMapper.fromResultSet (rs));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return result;
    }

    @Override
    public int countAll () {
        AtomicInteger reference = new AtomicInteger ();
        
        try {
            String query = "SELECT COUNT(*) FROM `cargo` WHERE 1";
            DBConnectionProducer.query (query, false, (st, __) -> {
                // nothing to initialize
            }, (rs, __) -> {
                reference.set (rs.getInt (1));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return reference.get ();
    }
    
}
