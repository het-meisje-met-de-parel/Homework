package shipping.exceptions;


public class IdentifierMissedException extends RuntimeException {
    
    public IdentifierMissedException () {
        super ();
    }
    
    public IdentifierMissedException (String message) {
        super (message);
    }
    
}
