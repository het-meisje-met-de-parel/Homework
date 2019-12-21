package shipping.exceptions;

public class NotExistingEntityExeption extends Exception  {

    public NotExistingEntityExeption(){

    }

    public NotExistingEntityExeption(String message) {
        super(message);
    }
}
