package doubleone.mobilesearch.exception;

public class NotFoundException extends AppException{

    private static final long serialVersionUID = 1L;

    public NotFoundException(String code, String message){
        super(code, message);
    }
}