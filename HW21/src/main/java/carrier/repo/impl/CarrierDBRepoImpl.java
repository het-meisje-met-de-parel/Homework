package carrier.repo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import application.db.DBConnectionProducer;
import carrier.domain.Carrier;
import carrier.repo.CarrierMapper;
import carrier.repo.CarrierRepo;

public class CarrierDBRepoImpl implements CarrierRepo {

    @Override
    public Optional <Carrier> findById (Long id) {
        AtomicReference <Carrier> reference = new AtomicReference <> ();
        
        try {
            String query = "SELECT * FROM `carrier` WHERE `id` = ?";
            DBConnectionProducer.query (query, false, (st, __) -> {
                st.setLong (1, id);
            }, (rs, __) -> {
                reference.set (CarrierMapper.fromResultSet (rs));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return Optional.ofNullable (reference.get ());
    }

    @Override
    public void save (Carrier entity) {
        try {
            String query = "INSERT `carrier` ( "
                         + "  `id`, `name`, `address`, `carrier_type` "
                         + ") VALUES (?, ?, ?, ?)";
            DBConnectionProducer.query (query, true, (st, __) -> {
                CarrierMapper.initInsertStatement (st, entity);
            }, (__, rows) -> {
                // nothing to do
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
    }

    @Override
    public boolean update (Carrier entity) {
        AtomicBoolean reference = new AtomicBoolean ();
        
        try {
            String query = "UPDATE `carrier` SET "
                         + "  `name` = ?, "
                         + "  `address` = ?, "
                         + "  `carrier_type` = ? "
                         + "WHERE `id` = ?";
            DBConnectionProducer.query (query, true, (st, __) -> {
                CarrierMapper.initUpdateStatement (st, entity);
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
            String query = "DELETE FROM `carrier` WHERE `id` = ?";
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
    public List <Carrier> getAll () {
        List <Carrier> result = new ArrayList <> ();
        
        try {
            String query = "SELECT * FROM `carrier` WHERE 1";
            DBConnectionProducer.query (query, false, (st, __) -> {
                // nothing to initialize
            }, (rs, __) -> {
                result.add (CarrierMapper.fromResultSet (rs));
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
            String query = "SELECT COUNT(*) FROM `carrier` WHERE 1";
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

    @Override
    public Optional <Carrier> getByIdFetchingTransportations (long id) {
        return findById (id);
    }

    @Override
    public Carrier [] findByName (String name) {
        List <Carrier> result = new ArrayList <> ();
        
        try {
            String query = "SELECT * FROM `carrier` WHERE `name` = ?";
            DBConnectionProducer.query (query, false, (st, __) -> {
                st.setString (1, name);
            }, (rs, __) -> {
                result.add (CarrierMapper.fromResultSet (rs));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return result.toArray (Carrier []::new);
    }
    
}
