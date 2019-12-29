package storage.initor;

public final class StorageInitorFactory {

    private static final String RESORCES_PREFIX = "/ru/epam/javacore/lesson_11_generics_and_io/";
    
  private StorageInitorFactory() {}

  public static StorageInitor getStorageInitor(InitStorageType initStorageType){
    switch (initStorageType){
      case MEMORY: {
        return new InMemoryStorageInitor();
      }
      case TEXT_FILE: {
        return new TextFileDataInitor();
      }
      case XML_FILE: {
          return new XMLFileDataInitor (RESORCES_PREFIX + "init-data.xml");
      }
      default:{
        throw new RuntimeException("Unknown storage init type " + initStorageType);
      }
    }
  }

}
