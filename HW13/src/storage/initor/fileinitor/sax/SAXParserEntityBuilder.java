package storage.initor.fileinitor.sax;

import java.util.Map.Entry;

import org.xml.sax.Attributes;

import common.business.domain.BaseEntity;

public interface SAXParserEntityBuilder <T extends BaseEntity> {
    
    void handleOpeningTag (String tag, Attributes attributes);
    
    void handleClosingTag (String tag);
    
    void handleString (String value);
    
    Entry <String, T> build ();
    
    String getResponsibleTag ();
    
}
