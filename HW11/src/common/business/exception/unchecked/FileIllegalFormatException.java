package common.business.exception.unchecked;

public class FileIllegalFormatException extends RuntimeException {
    
    public FileIllegalFormatException () {
        super ();
    }
    
    public FileIllegalFormatException (String message) {
        super (message);
    }
    
}
