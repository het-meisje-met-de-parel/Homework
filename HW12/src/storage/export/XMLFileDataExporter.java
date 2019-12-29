package storage.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.serviceholder.ServiceHolder;
import cargo.domain.Cargo;
import cargo.service.CargoService;
import carrier.domain.Carrier;
import carrier.service.CarrierService;
import common.business.exception.checked.ExportStorageException;
import storage.initor.XMLFileDataInitor;
import transportation.domain.Transportation;
import transportation.service.TransportationService;

public class XMLFileDataExporter implements StorageExporter {

    private final ServiceHolder serviceHolder = ServiceHolder.getInstance ();
    
    private final TransportationService transportationService = 
        serviceHolder.getTransportationService ();
    
    private final CarrierService carrierService = 
        serviceHolder.getCarrierService ();
    
    private final CargoService cargoService = 
        serviceHolder.getCargoService ();
    
    private final String filePath;
    
    public XMLFileDataExporter (String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void exportStoarage () throws ExportStorageException {
        if (!filePath.endsWith (".xml")) {
            String message = "Export file should have `.xml` extension";
            throw new ExportStorageException (message);
        }
        
        Document document = createDocument ();
        
        Element storage = document.createElement ("storage");
        document.appendChild (storage);
        
        Element cargos = document.createElement (XMLFileDataInitor.CARGOS_TAG_NAME);
        var id2cargoIndex = exportCargos (document, cargos);
        storage.appendChild (cargos);
        
        Element carriers = document.createElement (XMLFileDataInitor.CARRIERS_TAG_NAME);
        var id2carrierIndex = exportCarriers (document, carriers);
        storage.appendChild (carriers);
        
        Element transportations = document.createElement (XMLFileDataInitor.TRANSPORTATIONS_TAG_NAME);
        exportTransportations (document, transportations, id2cargoIndex, id2carrierIndex);
        storage.appendChild (transportations);
        
        writeFile (document);
    }
    
    private Document createDocument () throws ExportStorageException {
        try {
            final var builder = DocumentBuilderFactory.newInstance ()
                . newDocumentBuilder ();
            return builder.newDocument ();
        } catch (ParserConfigurationException pce) {
            throw new ExportStorageException (pce.getMessage ());
        }
    }
    
    private void writeFile (Document document) {
        File file = new File (filePath);
        
        try (
            var w = new FileWriter (file);
        ) {
            final var transformer = TransformerFactory.newInstance ()
                . newTransformer ();
            StreamResult sr = new StreamResult (w);
            
            DOMSource src = new DOMSource (document);
            transformer.transform (src, sr);
        } catch (TransformerException | IOException es) {
            throw new ExportStorageException (es.getMessage ());
        }
    }
    
    private Map <Long, Long> exportCargos (Document document, Element parent) {
        Map <Long, Long> id2index = new HashMap <> ();
        long index = 0;
        
        for (Cargo cargo : cargoService.getAll ()) {
            Element node = document.createElement (XMLFileDataInitor.CARGO_TAG_NAME);
            parent.appendChild (node);
            
            node.setAttribute ("id", String.valueOf (index));
            id2index.put (cargo.getId (), index);
            index++;
            
            node.setAttribute ("type", cargo.getCargoType ().name ().toLowerCase ());
            node.setAttribute ("weight", String.valueOf (cargo.getWeight ()));
            node.setAttribute ("name", cargo.getName ());
        }
        
        return id2index;
    }
    
    private Map <Long, Long> exportCarriers (Document document, Element parent) {
        Map <Long, Long> id2index = new HashMap <> ();
        long index = 0;
        
        for (Carrier carrier : carrierService.getAll ()) {
            Element node = document.createElement (XMLFileDataInitor.CARRIER_TAG_NAME);
            parent.appendChild (node);
            
            node.setAttribute ("id", String.valueOf (index));
            id2index.put (carrier.getId (), index);
            index++;
            
            node.setAttribute ("type", carrier.getCarrierType ().name ().toLowerCase ());
            node.setAttribute ("address", carrier.getAddress ());
            node.setAttribute ("name", carrier.getName ());
        }
        
        return id2index;
    }
    
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat ("dd.MM.yyyy");
    
    private void exportTransportations (Document document, Element parent, Map <Long, Long> id2cargoIndex, 
            Map <Long, Long> id2carrierIndex) {
        for (Transportation transportation : transportationService.getAll ()) {
            Element node = document.createElement (XMLFileDataInitor.TRANSPORTATION_TAG_NAME);
            parent.appendChild (node);
            
            long carrierId = transportation.getCarrier ().getId ();
            node.setAttribute ("carrier", String.valueOf (id2carrierIndex.get (carrierId)));
            
            long cargoId = transportation.getCargo ().getId ();
            node.setAttribute ("cargo", String.valueOf (id2cargoIndex.get (cargoId)));
            
            node.setAttribute ("date", DATE_FORMAT.format (transportation.getTransportationBeginDate ()));
            node.setAttribute ("description", transportation.getDescription ());
            node.setAttribute ("billTo", transportation.getBillTo ());
        }
    }
    
}
