package storage.initor.fileinitor.sax;

import java.text.ParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.Map.Entry;

import org.xml.sax.Attributes;

import common.solutions.utils.JavaUtilDateUtils;
import storage.initor.fileinitor.BaseFileInitor.ParsedTransportation;
import transportation.domain.Transportation;

public class SAXParserTransportationEntityBuilder implements SAXParserEntityBuilder <Transportation> {

    public static final String RESPONSIBLE_TAG = "transportation";
    
    private ParsedTransportation transportation = new ParsedTransportation ();
    private String buffer;
    
    @Override
    public void handleOpeningTag (String tag, Attributes attributes) {
        switch (tag) {
            case RESPONSIBLE_TAG: {
                transportation.setCarrierRef (attributes.getValue ("carrierref"));
                transportation.setCargoRef (attributes.getValue ("cargoref"));
            } break;
        }
    }

    @Override
    public void handleClosingTag (String tag) {
        switch (tag) {
            case "billTo": {
                transportation.setBillTo (buffer);
            } break;
            
            case "description": {
                transportation.setDescription (buffer);
            } break;
            
            case "transportationBeginDate": {
                Date date = null;
                try {
                    date = JavaUtilDateUtils.valueOf (buffer);
                } catch (ParseException e) {
                    // TODO: rise exception up
                }
                
                transportation.setTransportationBeginDate (date);
            } break;
        }
        
        buffer = null;
    }

    @Override
    public void handleString (String value) {
        buffer = value.strip ();
    }

    @Override
    public Entry <String, Transportation> build () {
        return new SimpleEntry <> ("", transportation);
    }

    @Override
    public String getResponsibleTag () {
        return RESPONSIBLE_TAG;
    }
    
}
