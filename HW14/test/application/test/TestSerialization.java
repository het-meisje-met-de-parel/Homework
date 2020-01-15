package application.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import org.junit.jupiter.api.Test;

import cargo.domain.Cargo;
import cargo.domain.FoodCargo;
import carrier.domain.Carrier;
import carrier.domain.CarrierType;
import common.solutions.utils.SerializationUtils;
import transportation.domain.Transportation;

public class TestSerialization {
    
    private static final File SER_FILE_DIR = new File ("serialized");
    
    private static final Cargo cargo = new FoodCargo ();
    
    static {
        cargo.setName ("Apple");
        cargo.setWeight (143);
        cargo.setId (1L);
    }
    
    private static final Carrier carrier = new Carrier ();
    
    static {
        carrier.setCarrierType (CarrierType.CAR);
        carrier.setAddress ("Moscow, Russia");
        carrier.setName ("Carrier B");
        carrier.setId (1L);
    }
    
    @Test
    public void testCargoSerialization () throws IOException {
        writeAndReadAndCompare ("cargo", cargo);
    }
    
    @Test
    public void testCarrierSerialization () throws IOException {        
        writeAndReadAndCompare ("carrier", carrier);
    }
    
    @Test
    public void testTransporationSerialization () throws IOException {
        Transportation transportation = new Transportation ();
        transportation.setTransportationBeginDate (Date.valueOf ("2012-01-15"));
        transportation.setCarrier (carrier);
        transportation.setCargo (cargo);
        transportation.setBillTo ("Me");
        
        writeAndReadAndCompare ("transportation", transportation);
    }
    
    private void writeAndReadAndCompare (String filename, Object object) throws IOException {
        File file = new File (SER_FILE_DIR, filename + ".data");
        SerializationUtils.serializeToFile (file, object);
        
        Object read = SerializationUtils.readSerializedFromFile (file);
        assertNotNull (read);
        
        assertEquals (object.toString (), read.toString ());
    }
    
}
