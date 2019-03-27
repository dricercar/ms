package doubleone.mobilesearch.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import doubleone.mobilesearch.entity.SessionBean;

@Configuration
/**
 * SessionBeanConfig
 */
public class SessionBeanConfig {

    @Bean
    @Scope(value="session")
    public SessionBean createSessionBean(){
        return new SessionBean();
    }
}
