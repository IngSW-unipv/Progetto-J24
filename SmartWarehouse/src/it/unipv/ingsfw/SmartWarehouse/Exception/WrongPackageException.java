package it.unipv.ingsfw.SmartWarehouse.Exception;

public class WrongPackageException extends Exception {

    private static final long serialVersionUID = 1L;
    public WrongPackageException(String errorMessage) {
        super(errorMessage); 
    }
}
