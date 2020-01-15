package common.solutions.utils;

import java.io.*;

public class SerializationUtils {
    
    public static void serializeToFile (File file, Object object) throws IOException {
        try (
            var os  = new FileOutputStream (file);
            var oos = new ObjectOutputStream (os);
        ) {
            oos.writeObject (object);
        }
    }
    
    public static <R> R readSerializedFromFile (File file) throws IOException {
        try (
            var is  = new FileInputStream (file);
            var ois = new ObjectInputStream (is);
        ) {
            @SuppressWarnings ("unchecked")
            R object = (R) ois.readObject ();
            
            return object;
        } catch (ClassNotFoundException cnfe) {
            throw new IOException (cnfe);
        }
    }
    
}
