package storage.initor.fileinitor.sax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cargo.domain.Cargo;
import carrier.domain.Carrier;
import storage.initor.fileinitor.BaseFileInitor.ParsedTransportation;
import transportation.domain.Transportation;

public class SAXParserHandler extends DefaultHandler {
    
    private final List <ParsedTransportation> ptransportations = new ArrayList <> ();
    
    private final List <Transportation> transportations = new ArrayList <> ();
    private final Map <String, Carrier> id2carrier = new HashMap <> ();
    private final Map <String, Cargo> id2cargo = new HashMap <> ();
    
    private SAXParserEntityBuilder <?> builder;
    
    @Override
    public void startElement (
        String uri, String localName, String qName, Attributes attributes
    ) throws SAXException {
        switch (qName) {
            case SAXParserCargoEntityBuilder.RESPONSIBLE_TAG: {
                builder = new SAXParserCargoEntityBuilder ();
                builder.handleOpeningTag (qName, attributes);
            } break;
            
            case SAXParserCarrierEntityBuilder.RESPONSIBLE_TAG: {
                builder = new SAXParserCarrierEntityBuilder ();
                builder.handleOpeningTag (qName, attributes);
            } break;
            
            case SAXParserTransportationEntityBuilder.RESPONSIBLE_TAG: {
                builder = new SAXParserTransportationEntityBuilder ();
                builder.handleOpeningTag (qName, attributes);
            } break;
            
            default: {
                if (builder != null) {                    
                    builder.handleOpeningTag (qName, attributes);
                }
            }
        }
    }
    
    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
        if (builder == null) { return; }
        
        if (builder.getResponsibleTag ().equals (qName)) {
            Entry <String, ?> entry = builder.build ();
            
            if (entry != null) {
                if (entry.getValue () instanceof Cargo) {
                    Cargo cargo = (Cargo) entry.getValue ();
                    id2cargo.put (entry.getKey (), cargo);
                } else if (entry.getValue () instanceof Carrier) {
                    Carrier carrier = (Carrier) entry.getValue ();
                    id2carrier.put (entry.getKey (), carrier);
                } else if (entry.getValue () instanceof ParsedTransportation) {
                    ParsedTransportation transportation = (ParsedTransportation) entry.getValue ();
                    ptransportations.add (transportation);
                }
            }
            
            builder = null;
        } else {
            builder.handleClosingTag (qName);
        }
    }
    
    @Override
    public void endDocument () throws SAXException {
        for (ParsedTransportation parsedTransportation : ptransportations) {
            String carrierId = parsedTransportation.getCarrierRef ();
            Carrier carrier = id2carrier.get (carrierId);
            if (carrier == null) {
                String message = "Unknown carrier identifier in transportation: " 
                               + carrierId;
                throw new SAXException (message);
            }
            
            String cargoId = parsedTransportation.getCargoRef ();
            Cargo cargo = id2cargo.get (cargoId);
            if (cargo == null) {
                String message = "Unknown cargo identifier in transportation: " 
                               + cargoId;
                throw new SAXException (message);
            }
            
            transportations.add (parsedTransportation);
            parsedTransportation.setCarrier (carrier);
            parsedTransportation.setCargo (cargo);            
        }
        
        ptransportations.clear ();
    }
    
    @Override
    public void characters (char [] ch, int start, int length) throws SAXException {
        if (builder == null) { return; }
        
        builder.handleString (new String (ch, start, length));
    }
    
    public List <Cargo> getCargos () {
        return List.copyOf (id2cargo.values ());
    }
    
    public List <Carrier> getCarriers () {
        return List.copyOf (id2carrier.values ());
    }
    
    public List <Transportation> getTransportations () {
        return List.copyOf (transportations);
    }
    
}
