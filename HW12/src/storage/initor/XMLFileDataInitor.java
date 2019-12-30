package storage.initor;

import java.io.IOException;
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
import common.business.domain.BaseEntity;
import common.business.exception.checked.InitStorageException;
import common.business.service.CommonService;
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
        
        Document document = parseFileToDocument ();
        
        NodeList tags = document.getElementsByTagName (CARGOS_TAG_NAME);
        var id2cargo = this.<Cargo> importEntities (
            tags.getLength () > 0 ? tags.item (0) : null, 
            CARGO_TAG_NAME
        );
        
        tags = document.getElementsByTagName (CARRIERS_TAG_NAME);
        var id2carrier = this.<Carrier> importEntities (
            tags.getLength () > 0 ? tags.item (0) : null, 
            CARRIER_TAG_NAME
        );
        
        tags = document.getElementsByTagName (TRANSPORTATIONS_TAG_NAME);
        importTransportations (tags.getLength () > 0 ? tags.item (0) : null, id2cargo, id2carrier);
    }
    
    private Document parseFileToDocument () throws InitStorageException {
        try (
            var is = XMLFileDataInitor.class.getResourceAsStream (filePath);
        ) {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance ()
                . newDocumentBuilder ();
            return builder.parse (is);
        } catch (ParserConfigurationException | SAXException | IOException es) {
            throw new InitStorageException (es.getMessage ());
        }
        
    }
    
    private <T extends BaseEntity> Map <Long, T> importEntities (
        Node parent, String containerTag
    ) throws InitStorageException {
        if (parent == null) { return Map.of (); }

        Element parentEl = (Element) parent;
        NodeList list = parentEl.getElementsByTagName (containerTag);
        
        Map <Long, T> id2entity = new HashMap <> ();
        CommonService <T, Long> service = null;
        
        for (int i = 0; i < list.getLength (); i++) {
            final Element cargo = (Element) list.item (i);
            if (!cargo.hasAttribute ("id")) { continue; }
            
            T entity = null;
            switch (containerTag) {
                case CARGO_TAG_NAME: {
                    @SuppressWarnings ("unchecked")
                    var tmp = (CommonService <T, Long>) cargoService;
                    service = tmp;
                    
                    @SuppressWarnings ("unchecked")
                    var tmp2 = (T) readCargo (cargo);
                    entity = tmp2;
                } break;
                
                case CARRIER_TAG_NAME: {
                    @SuppressWarnings ("unchecked")
                    var tmp = (CommonService <T, Long>) carrierService;
                    service = tmp;
                    
                    @SuppressWarnings ("unchecked")
                    var tmp2 = (T) readCarrier (cargo);
                    entity = tmp2;
                } break;
            }
            
            if (id2entity.containsKey (entity.getId ())) {
                String message = "Duplicating ID of cargos: " + entity.getId ();
                throw new InitStorageException (message);
            }
            
            id2entity.put (entity.getId (), entity);
            service.save (entity);
        }
        
        return id2entity;
    }
    
    private Cargo readCargo (Element element) {
        CargoType type = CargoType.valueOf (element.getAttribute ("type").toUpperCase ());
        int weight = Integer.parseInt (element.getAttribute ("weight"));
        long id = Long.parseLong (element.getAttribute ("id"));
        String name = element.getAttribute ("name");
        
        Cargo cargo = null;
        switch (type) {
            case FOOD:
                cargo = new FoodCargo ();
                break;
            case CLOTHERS:
                cargo = new ClothersCargo ();
                break;
        }
        
        cargo.setTransportations (new ArrayList <> ());
        cargo.setWeight (weight);
        cargo.setName (name);
        cargo.setId (id);
        
        return cargo;
    }
    
    private Carrier readCarrier (Element element) {        
        CarrierType type = CarrierType.valueOf (element.getAttribute ("type").toUpperCase ());
        long id = Long.parseLong (element.getAttribute ("id"));
        String address = element.getAttribute ("address");
        String name = element.getAttribute ("name");
        
        Carrier carrier = new Carrier ();
        
        carrier.setTransportations (new ArrayList <> ());
        carrier.setCarrierType (type);
        carrier.setAddress (address);
        carrier.setName (name);
        carrier.setId (id);
        
        return carrier;
    }
    
    private Transportation readTransportation (Element element, Map <Long, Cargo> id2cargo, 
            Map <Long, Carrier> id2carrier) throws InitStorageException {
        long cargoId = Long.parseLong (element.getAttribute ("cargo"));
        if (!id2cargo.containsKey (cargoId)) {
            String message = "Unknown cargo identifier in transportation: " + cargoId;
            throw new InitStorageException (message);
        }
        
        long carrierId = Long.parseLong (element.getAttribute ("carrier"));
        if (!id2carrier.containsKey (carrierId)) {
            String message = "Unknown carrier identifier in transportation: " + carrierId;
            throw new InitStorageException (message);
        }
        
        Date startDate = null;
        try {
            startDate = JavaUtilDataUtils.valueOf (element.getAttribute ("date"));
        } catch (ParseException e) {
            startDate = new Date ();
        }
        String description = element.getAttribute ("description");
        String billTo = element.getAttribute ("billTo");
        
        Transportation transportation = new Transportation ();
        transportation.setTransportationBeginDate (startDate);
        transportation.setDescription (description);
        transportation.setBillTo (billTo);
        
        Cargo cargo = id2cargo.get (cargoId);
        transportation.setCargo (cargo);
        
        Carrier carrier = id2carrier.get (carrierId);
        transportation.setCarrier (carrier);
        
        return transportation;
    }
    
    private void importTransportations (Node transportationsParent, Map <Long, Cargo> id2cargo, 
            Map <Long, Carrier> id2carrier) throws InitStorageException {
        if (transportationsParent == null || id2cargo == null || id2carrier == null) {
            return;
        }
        
        Element parent = (Element) transportationsParent;
        NodeList list = parent.getElementsByTagName (TRANSPORTATION_TAG_NAME);
        
        for (int i = 0; i < list.getLength (); i++) {
            Transportation transportation = readTransportation (
                (Element) list.item (i), id2cargo, id2carrier
            );
            
            transportation.getCarrier ().getTransportations ().add (transportation);
            transportation.getCargo ().getTransportations ().add (transportation);
            
            transportationService.save (transportation);
        }
    }
    
}
