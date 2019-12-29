package storage.initor;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.serviceholder.ServiceHolder;
import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;
import cargo.service.CargoService;
import carrier.domain.Carrier;
import carrier.domain.CarrierType;
import carrier.service.CarrierService;
import common.business.exception.checked.InitStorageException;
import common.solutions.utils.JavaUtilDataUtils;
import transportation.domain.Transportation;
import transportation.service.TransportationService;

public class XMLFileDataInitor implements StorageInitor {

    public static final String CARGOS_TAG_NAME = "cargos";
    public static final String CARGO_TAG_NAME = "cargo";
    public static final String CARRIERS_TAG_NAME = "carriers";
    public static final String CARRIER_TAG_NAME = "carrier";
    public static final String TRANSPORTATIONS_TAG_NAME = "transportations";
    public static final String TRANSPORTATION_TAG_NAME = "transportation";
    
    private final ServiceHolder serviceHolder = ServiceHolder.getInstance ();
    
    private final TransportationService transportationService = 
        serviceHolder.getTransportationService ();
    
    private final CarrierService carrierService = 
        serviceHolder.getCarrierService ();
    
    private final CargoService cargoService = 
        serviceHolder.getCargoService ();
    
    private final String filePath;
    
    public XMLFileDataInitor (String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void initStorage () throws InitStorageException {
        if (!filePath.endsWith (".xml")) {
            String message = "Import file should have `.xml` extension";
            throw new InitStorageException (message);
        }
        
        Document document = null;
        try (
            var is = XMLFileDataInitor.class.getResourceAsStream (filePath);
        ) {
            document = parseInputStreamToDOM (is);
        } catch (IOException ioe) {
            throw new InitStorageException (ioe.getMessage ());
        }
        
        NodeList tags = document.getElementsByTagName (CARGOS_TAG_NAME);
        var id2cargo = importCargos (tags.getLength () > 0 ? tags.item (0) : null);
        
        tags = document.getElementsByTagName (CARRIERS_TAG_NAME);
        var id2carrier = importCarriers (tags.getLength () > 0 ? tags.item (0) : null);
        
        tags = document.getElementsByTagName (TRANSPORTATIONS_TAG_NAME);
        importTransportations (tags.getLength () > 0 ? tags.item (0) : null, id2cargo, id2carrier);
    }
    
    private Document parseInputStreamToDOM (InputStream is) throws InitStorageException {
        DocumentBuilder builder = null; 
        
        try {
            builder = DocumentBuilderFactory.newInstance ()
                    . newDocumentBuilder ();
            return builder.parse (is);
        } catch (ParserConfigurationException | SAXException | IOException es) {
            throw new InitStorageException (es.getMessage ());
        }
        
    }
    
    private Map <Long, Cargo> importCargos (Node cargosParent) throws InitStorageException {
        if (cargosParent == null) {
            return Map.of ();
        }
        
        Element parent = (Element) cargosParent;
        
        Map <Long, Cargo> id2cargo = new HashMap <> ();
        NodeList list = parent.getElementsByTagName (CARGO_TAG_NAME);
        
        for (int i = 0; i < list.getLength (); i++) {
            final Element cargo = (Element) list.item (i);
            if (!cargo.hasAttribute ("id")) { continue; }
            
            long id = Long.parseLong (cargo.getAttribute ("id"));
            if (id2cargo.containsKey (id)) {
                String message = "Duplicating ID of cargos: " + id;
                throw new InitStorageException (message);
            }
            
            CargoType type = CargoType.valueOf (cargo.getAttribute ("type").toUpperCase ());
            int weight = Integer.parseInt (cargo.getAttribute ("weight"));
            String name = cargo.getAttribute ("name");
            
            Cargo cargoObj = null;
            switch (type) {
                case FOOD:
                    cargoObj = new FoodCargo ();
                    break;
                case CLOTHERS:
                    cargoObj = new ClothersCargo ();
                    break;
            }
            
            cargoObj.setTransportations (new ArrayList <> ());
            cargoObj.setWeight (weight);
            cargoObj.setName (name);
            
            cargoService.save (cargoObj);
            id2cargo.put (id, cargoObj);
        }
        
        return id2cargo;
    }
    
    private Map <Long, Carrier> importCarriers (Node carriersParent) throws InitStorageException {
        if (carriersParent == null) {
            return Map.of ();
        }
        
        Element parent = (Element) carriersParent;
        
        Map <Long, Carrier> id2carrier = new HashMap <> ();
        NodeList list = parent.getElementsByTagName (CARRIER_TAG_NAME);
        
        for (int i = 0; i < list.getLength (); i++) {
            final Element carrier = (Element) list.item (i);
            if (!carrier.hasAttribute ("id")) { continue; }
            
            long id = Long.parseLong (carrier.getAttribute ("id"));
            if (id2carrier.containsKey (id)) {
                String message = "Duplicating ID of carriers: " + id;
                throw new InitStorageException (message);
            }
            
            CarrierType type = CarrierType.valueOf (carrier.getAttribute ("type").toUpperCase ());
            String address = carrier.getAttribute ("address");
            String name = carrier.getAttribute ("name");
            
            Carrier carrierObj = new Carrier ();
            
            carrierObj.setTransportations (new ArrayList <> ());
            carrierObj.setCarrierType (type);
            carrierObj.setAddress (address);
            carrierObj.setName (name);
            
            carrierService.save (carrierObj);
            id2carrier.put (id, carrierObj);
        }
        
        return id2carrier;
    }
    
    private void importTransportations (Node transportationsParent, Map <Long, Cargo> id2cargo, 
            Map <Long, Carrier> id2carrier) throws InitStorageException {
        if (transportationsParent == null || id2cargo == null || id2carrier == null) {
            return;
        }
        
        Element parent = (Element) transportationsParent;
        
        NodeList list = parent.getElementsByTagName (TRANSPORTATION_TAG_NAME);
        
        for (int i = 0; i < list.getLength (); i++) {
            final Element transportation = (Element) list.item (i);
            
            long cargoId = Long.parseLong (transportation.getAttribute ("cargo"));
            if (!id2cargo.containsKey (cargoId)) {
                String message = "Unknown cargo identifier in transportation: " + cargoId;
                throw new InitStorageException (message);
            }
            
            long carrierId = Long.parseLong (transportation.getAttribute ("carrier"));
            if (!id2carrier.containsKey (carrierId)) {
                String message = "Unknown carrier identifier in transportation: " + carrierId;
                throw new InitStorageException (message);
            }
            
            Date startDate = null;
            try {
                startDate = JavaUtilDataUtils.valueOf (transportation.getAttribute ("date"));
            } catch (ParseException e) {
                startDate = new Date ();
            }
            String description = transportation.getAttribute ("description");
            String billTo = transportation.getAttribute ("billTo");
            
            Transportation transportationObj = new Transportation ();
            transportationObj.setTransportationBeginDate (startDate);
            transportationObj.setDescription (description);
            transportationObj.setBillTo (billTo);
            
            Cargo cargo = id2cargo.get (cargoId);
            transportationObj.setCargo (cargo);
            cargo.getTransportations ().add (transportationObj);
            
            Carrier carrier = id2carrier.get (carrierId);
            transportationObj.setCarrier (carrier);
            carrier.getTransportations ().add (transportationObj);
            
            transportationService.save (transportationObj);
        }
    }
    
}
