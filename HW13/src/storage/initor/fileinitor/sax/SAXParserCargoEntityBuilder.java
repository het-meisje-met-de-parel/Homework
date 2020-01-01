package storage.initor.fileinitor.sax;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.xml.sax.Attributes;

import cargo.domain.Cargo;
import cargo.domain.CargoType;
import cargo.domain.ClothersCargo;
import cargo.domain.FoodCargo;

public class SAXParserCargoEntityBuilder implements SAXParserEntityBuilder <Cargo> {

    public static final String RESPONSIBLE_TAG = "cargo";
    
    private String buffer;
    private Cargo cargo;
    private String ref;
    
    @Override
    public void handleOpeningTag (String tag, Attributes attributes) {
        switch (tag) {
            case RESPONSIBLE_TAG: {
                ref = attributes.getValue ("id");
                
                CargoType type = CargoType.valueOf (attributes.getValue ("type"));
                switch (type) {
                    case CLOTHERS: {
                        cargo = new ClothersCargo ();
                    } break;
                    
                    case FOOD: {
                        cargo = new FoodCargo ();
                    } break;
                }
            } break;
        }
    }

    @Override
    public void handleClosingTag (String tag) {
        switch (tag) {
            case "name": {
                cargo.setName (buffer);
            } break;
            
            case "weight": {
                int weight = Integer.parseInt (buffer);
                cargo.setWeight (weight);
            } break;
        }
        
        buffer = null;
    }

    @Override
    public void handleString (String value) {
        buffer = value.strip ();
    }

    @Override
    public Entry <String, Cargo> build () {
        return new SimpleEntry <> (ref, cargo);
    }

    @Override
    public String getResponsibleTag () {
        return RESPONSIBLE_TAG;
    }
    
}
