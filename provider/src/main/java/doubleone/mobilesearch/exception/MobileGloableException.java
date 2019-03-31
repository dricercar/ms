package doubleone.mobilesearch.exception;
/**
 * 统一异常处理
 */

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import doubleone.mobilesearch.entity.Payload;

@RestControllerAdvice
public class MobileGloableException{
    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value=Exception.class)
    public Payload<Void> gloableExceptionHandler(Exception e){
        return new Payload<Void>(null, "500", e.getMessage());
    }

    @ExceptionHandler(value=AppException.class)
    public Payload<Void> appExceptinHandler(AppException e){
        return new Payload<Void>(null, e.getCode(), e.getMessage());
    }
}