package storage.initor.fileinitor.sax;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import common.business.exception.checked.InitStorageException;
import storage.initor.fileinitor.BaseFileInitor;

public class SAXParserFileDataInitor extends BaseFileInitor {

    private static final String FILE = "/ru/epam/javacore/initdata/xmldata.xml";
    
    private SAXParserHandler handler = new SAXParserHandler ();
    
    @Override
    public void initStorage () throws InitStorageException {
        parseXMLFile ();
        
        persistCarriers (handler.getCarriers ());
        persistCargos (handler.getCargos ());
    }
    
    private void parseXMLFile () throws InitStorageException {
        try (
            var is = SAXParserFileDataInitor.class.getResourceAsStream (FILE);
        ) {
            SAXParserFactory factory = SAXParserFactory.newInstance ();            
            SAXParser parser = factory.newSAXParser ();
            
            parser.parse (is, handler, "");
        } catch (SAXException | ParserConfigurationException saxe) {
            throw new InitStorageException (saxe.getMessage ());
        } catch (IOException ioe) {
            throw new InitStorageException (ioe.getMessage ());
        }
    }
    
}
