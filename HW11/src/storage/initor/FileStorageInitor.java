package storage.initor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

import application.serviceholder.ServiceHolder;
import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;
import cargo.service.CargoService;
import carrier.domain.Carrier;
import carrier.domain.CarrierType;
import carrier.service.CarrierService;
import transportation.domain.Transportation;
import transportation.service.TransportationService;

public class FileStorageInitor implements StorageInitor {

    private final ServiceHolder serviceHolder = ServiceHolder.getInstance ();
    
    private final TransportationService transportationService = 
        serviceHolder.getTransportationService ();
    
    private final CarrierService carrierService = 
        serviceHolder.getCarrierService ();
    
    private final CargoService cargoService = 
        serviceHolder.getCargoService ();
    
    private final String filePath;
    
    public FileStorageInitor (String storageFilePath) {
        filePath = storageFilePath;
    }
    
    @Override
    public void initStorage () throws IOException {
        final File file = new File (filePath);
        if (!file.isFile () || !file.canRead ()) {
            throw new IOException ("Failed to read storage file");
        }
        
        try (
            var is = new FileInputStream (file);
            var r  = new InputStreamReader (is, StandardCharsets.UTF_8);
            var br = new BufferedReader (r);
        ) {
            String line = null;
            int number = 0;
            
            while ((line = br.readLine ()) != null) {
                number++;
                
                if (line.strip ().length () == 0) { continue; }
                processStorageLine (number, line.strip ().split ("\\s+"));
            }
        }
    }
    
    private void processStorageLine (int line, String [] tokens) {
        switch (tokens [0].toLowerCase ()) {
            case "cargo":
                processCargo (line, tokens);
                break;
                
            case "carrier":
                processCarrier (line, tokens);
                break;
                
            case "transportation":
                processTransportation (line, tokens);
                break;
                
            default:
                if (!tokens [0].startsWith ("//")) {
                    String message = "Unexpected input in line " + line;
                    System.err.println (message);
                }
                break;
        }
    }
    
    private void processCargo (int line, String [] tokens) {
        if (tokens.length < 4) {
            System.err.println ("Not enough arguments for cargo object in line " + line);
            return;
        }
        
        String name = tokens [1];
        
        CargoType type = CargoType.valueOf (tokens [2].toUpperCase ());
        int weight = Integer.parseInt (tokens [3]);
        
        @SuppressWarnings ("preview")
        Cargo cargo = switch (type) {
            case FOOD     -> new FoodCargo ();
            case CLOTHERS -> new ClothersCargo ();
            default       -> null;
        };
        
        if (cargo == null) {
            System.err.println ("Cargo has unexpected type in line " + line);
            return;
        }
        
        cargo.setName (name);
        cargo.setWeight (weight);
        
        cargoService.save (cargo);
    }
    
    private void processCarrier (int line, String [] tokens) {
        if (tokens.length < 4) {
            System.err.println ("Not enough arguments for carrier object in line " + line);
            return;
        }
        
        String name = tokens [1];
        
        CarrierType type = CarrierType.valueOf (tokens [2].toUpperCase ());
        
        StringBuilder sb = new StringBuilder ();
        for (int i = 3; i < tokens.length; i++) {
            sb.append (tokens [i]).append (" ");
        }
        String address = sb.toString ().strip ();
        
        Carrier carrier = new Carrier ();
        
        carrier.setName (name);
        carrier.setCarrierType (type);
        carrier.setAddress (address);
        
        carrierService.save (carrier);
    }
    
    private void processTransportation (int line, String [] tokens) {
        if (tokens.length < 6) {
            System.err.println ("Not enough arguments for transportation object in line " + line);
            return;
        }
        
        long cargoId   = Long.parseLong (tokens [1]);
        long carrierId = Long.parseLong (tokens [2]);
        
        String billTo  = tokens [3];
        LocalDate date = LocalDate.parse (tokens [4]);
        
        StringBuilder sb = new StringBuilder ();
        for (int i = 5; i < tokens.length; i++) {
            sb.append (tokens [i]).append (" ");
        }
        String description = sb.toString ().strip ();
        
        Cargo cargo = cargoService.findById (cargoId);
        if (cargo == null) {
            System.err.println ("Wrong cargo id in line " + line);
            return;
        }
        
        Carrier carrier = carrierService.findById (carrierId);
        if (carrier == null) {
            System.err.println ("Wrong carrier id in line " + line);
            return;
        }
        
        Transportation transportation = new Transportation ();
        
        transportation.setCargo (cargo);
        transportation.setCarrier (carrier);
        transportation.setBillTo (billTo);
        transportation.setTransportationBeginDate (date);
        transportation.setDescription (description);
        
        transportationService.save (transportation);
        
        if (cargo.getTransportations () == null) {
            cargo.setTransportations (new ArrayList <> ());
        }
        
        cargo.getTransportations ().add (transportation);
        
        if (carrier.getTransportations () == null) {
            carrier.setTransportations (new ArrayList <> ());
        }
        
        carrier.getTransportations ().add (transportation);
    }
    
}
