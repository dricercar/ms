package doubleone.mobilesearch.config;

import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextListener;

/**
 * WebContextListener
 */
@Configuration
@WebListener
public class WebContextListener extends RequestContextListener{

    
}