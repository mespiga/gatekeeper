package app.exceptions;

public class NoProductException extends Exception{

    public NoProductException(String exc)
    {
        super(exc);
    }
    public String getMessage()
    {
        return super.getMessage();
    }
}