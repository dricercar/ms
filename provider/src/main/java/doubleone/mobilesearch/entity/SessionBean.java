package doubleone.mobilesearch.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

public class SessionBean {
    private static int i = 0;
    public SessionBean(){
        i++;
    }
    public int getI(){
        return i;
    }
    
    
}
