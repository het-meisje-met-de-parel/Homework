package common.business.exception.checked;


public class ExportStorageException extends RuntimeException {
    
    public ExportStorageException () {
        
    }
    
    public ExportStorageException (String message) {
        super (message);
    }
    
}
