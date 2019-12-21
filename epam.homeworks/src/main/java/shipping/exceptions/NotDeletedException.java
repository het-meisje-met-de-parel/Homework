package shipping.exceptions;

import shipping.RunTransportApplication;

public class NotDeletedException extends RuntimeException {

    public NotDeletedException () {
        super ();
    }

    public NotDeletedException (String message) {
        super (message);
    }

}
