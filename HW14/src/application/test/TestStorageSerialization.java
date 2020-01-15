package application.test;

import java.io.*;
import java.util.Arrays;

import application.Application;
import storage.Storage;

public class TestStorageSerialization {
    
    private static final File SER_FILE_DIR = new File ("serialized");
    private static final File SER_FILE = new File (SER_FILE_DIR, "storage.data");
    
    public static void main (String [] args) {
        requireAssertions ();
        
        Application.main (args);
        
        System.out.println ();
        System.out.println (Application.SEPARATOR + " Test serialization " + Application.SEPARATOR);
        
        try {            
            testSerialization ();
        } catch (IOException ioe) {
            ioe.printStackTrace ();
        }
    }
    
    private static void requireAssertions () {
        try {
            assert false;
            
            String message = "Enable asserions for this test via `-ea` flag";
            throw new IllegalStateException (message);
        } catch (AssertionError ae) {
            // it's expected behavior
        }
    }
    
    private static void testSerialization () throws IOException {
        final Storage storage = Storage.getInstance ();
        
        if (!SER_FILE_DIR.exists ()) {
            SER_FILE_DIR.mkdirs ();
        }
        
        writeStorage (storage, SER_FILE);
        System.out.println ("Serialized file size: " + SER_FILE.length () + " bytes");
        
        final Storage read = readStorage (SER_FILE);
        
        assert Arrays.equals (storage.cargoArray, read.cargoArray) 
             : "Arrays of Cargos are different";
        assert Arrays.equals (storage.carrierArray, read.carrierArray)
             : "Arrays of Carriers are different";
        assert Arrays.equals (storage.transportationArray, read.transportationArray)
             : "Arrays of Transportations are different";
        
        assert storage.cargoIndex == read.cargoIndex
             : "Array indices of Cargos are different";
        assert storage.carrierIndex == read.carrierIndex
             : "Array indices of Carriers are different";
        assert storage.transportationIndex == read.transportationIndex
             : "Array indices of Transportations are different";
        
        assert storage.cargoCollection.equals (read.cargoCollection)
             : "Collections of Cargos are different";
        assert storage.carrierCollection.equals (read.carrierCollection)
             : "Collections of Carriers are different";
        assert storage.transportationCollection.equals (read.transportationCollection)
             : "Collections of Transportations are different";
        
        System.out.println ("Storage is serialized and deserialized correctly");
    }
    
    private static void writeStorage (Storage storage, File file) throws IOException {
        try (
            var os = new FileOutputStream (file);
            var oos = new ObjectOutputStream (os);
        ) {
            oos.writeObject (storage);
        }
    }
    
    private static Storage readStorage (File file) throws IOException {
        try (
            var is = new FileInputStream (file);
            var ois = new ObjectInputStream (is);
        ) {
            return (Storage) ois.readObject ();
        } catch (ClassNotFoundException cnfe) {
            throw new IOException (cnfe);
        }
    }
    
}
