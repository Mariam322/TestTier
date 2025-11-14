package tn.iit.exceptions;

public class TiersNotFoundException extends RuntimeException {
    public TiersNotFoundException(String message) {
        super(message);
    }
}