package storage.initor;

import storage.initor.fileinitor.TextFileDataInitor;
import storage.initor.fileinitor.XmlDomFileDataInitor;
import storage.initor.fileinitor.sax.SAXParserFileDataInitor;

public final class StorageInitorFactory {

  private StorageInitorFactory() {

  }

  public static StorageInitor getStorageInitor(InitStorageType initStorageType) {
    switch (initStorageType) {

      case MEMORY: {
        return new InMemoryStorageInitor();
      }
      case TEXT_FILE: {
        return new TextFileDataInitor();
      }
      case XML_DOM_FILE:{
        return new XmlDomFileDataInitor();
      }
      case XML_SAX_PARSER : {
          return new SAXParserFileDataInitor ();
      }
      default: {
        throw new RuntimeException("Unknown storage init type " + initStorageType);
      }
    }
  }

}
