package storage.export;

import common.business.exception.checked.ExportStorageException;

public interface StorageExporter {
    
    void exportStoarage () throws ExportStorageException;
    
}
