package storage.initor;

public final class StorageInitorFactory {

  private StorageInitorFactory(){

  }

  public static StorageInitor getStorageInitor(InitStorageType initStorageType){
    switch (initStorageType){

      case MEMORY: {
        return new InMemoryStorageInitor();
      }
      case TEXT_FILE: {
        return new TextFileDataInitor();
      }
      default:{
        throw new RuntimeException("Unknown storage init type " + initStorageType);
      }
    }
  }

}
