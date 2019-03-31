package doubleone.mobilesearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NON_AUTHORITATIVE_INFORMATION, reason = "没有权限")
public class PermissionDeniedException extends AppException{

    private static final long serialVersionUID = 1L;

    public PermissionDeniedException(){
    }
    public PermissionDeniedException(String code, String msg){
        super(code, msg);
    }
}