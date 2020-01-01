package storage.initor.fileinitor.sax;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.xml.sax.Attributes;

import carrier.domain.Carrier;
import carrier.domain.CarrierType;

public class SAXParserCarrierEntityBuilder implements SAXParserEntityBuilder <Carrier> {

    public static final String RESPONSIBLE_TAG = "carrier";
    
    private Carrier carrier = new Carrier ();
    private String buffer;
    private String ref;
    
    @Override
    public void handleOpeningTag (String tag, Attributes attributes) {
        switch (tag) {
            case RESPONSIBLE_TAG: {
                ref = attributes.getValue ("id");
            } break;
        }
    }

    @Override
    public void handleClosingTag (String tag) {
        switch (tag) {
            case "name": {
                carrier.setName (buffer);
            } break;
            
            case "address": {
                carrier.setAddress (buffer);
            } break;
            
            case "type": {
                CarrierType type = CarrierType.valueOf (buffer);
                carrier.setCarrierType (type);
            } break;
        }
        
        buffer = null;
    }

    @Override
    public void handleString (String value) {
        buffer = value.strip ();
    }

    @Override
    public Entry <String, Carrier> build () {
        return new SimpleEntry <> (ref, carrier);
    }

    @Override
    public String getResponsibleTag () {
        return RESPONSIBLE_TAG;
    }
    
}
