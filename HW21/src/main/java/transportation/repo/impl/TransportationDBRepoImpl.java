package transportation.repo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import application.db.DBConnectionProducer;
import cargo.domain.Cargo;
import cargo.repo.CargoRepo;
import carrier.domain.Carrier;
import carrier.repo.CarrierRepo;
import lombok.Setter;
import transportation.domain.Transportation;
import transportation.repo.TransportationMapper;
import transportation.repo.TransportationRepo;

public class TransportationDBRepoImpl implements TransportationRepo {

    @Setter
    private CarrierRepo carrierRepo;
    
    @Setter 
    private CargoRepo cargoRepo;
    
    @Override
    public Optional <Transportation> findById (Long id) {
        AtomicReference <Transportation> reference = new AtomicReference <> ();
        
        try {
            String query = "SELECT * FROM `transportation` WHERE `id` = ?";
            DBConnectionProducer.query (query, false, (st, __) -> {
                st.setLong (1, id);
            }, (rs, __) -> {
                reference.set (TransportationMapper.fromResultSet (rs, cargoRepo, carrierRepo));
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
        
        return Optional.ofNullable (reference.get ());
    }

    @Override
    public void save (Transportation entity) {
        try {
            String query = "INSERT `transportation` ( "
                         + "  `id`, `cargo`, `carrier`, `description`, "
                         + "  `bill_to`, `begin_date` "
                         + ") VALUES (?, ?, ?, ?, ?, ?)";
            DBConnectionProducer.query (query, true, (st, __) -> {
                TransportationMapper.initInsertStatement (st, entity);
            }, (__, rows) -> {
                // nothing to do
            });
        } catch (SQLException sqle) {
            throw new RuntimeException (sqle);
        }
    }

    @Override
    public boolean update (Transportation entity) {
        AtomicBoolean reference = new AtomicBoolean ();
        
        try {
            String query = "UPDATE `transportation` SET "
                         + "  `cargo` = ?, "
                         + "  `carrier` = ?, "
                         + "  `description` = ?, "
                         + "  `bill_to` = ?, "
                         + "  `begin_date` = ? "
                         + "WHERE `id` = ?";
            DBConnectionProducer.query (query, true, (st, __) -> {
                TransportationMapper.initUpdateStatement (st, entity);
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
            String query = "DELETE FROM `transporatation` WHERE `id` = ?";
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
    public List <Transportation> getAll () {
        List <Transportation> result = new ArrayList <> ();
        
        try {
            String query = "SELECT * FROM `transportation` WHERE 1";
            DBConnectionProducer.query (query, false, (st, __) -> {
                // nothing to initialize
            }, (rs, __) -> {
                result.add (TransportationMapper.fromResultSet (rs, cargoRepo, carrierRepo));
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
            String query = "SELECT COUNT(*) FROM `transportation` WHERE 1";
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
    public List <Transportation> findByCargo (Cargo cargo) {
        return null;
    }

    @Override
    public List <Transportation> findByCarrier (Carrier carrier) {
        return null;
    }
    
}
