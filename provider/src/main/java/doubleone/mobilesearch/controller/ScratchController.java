package doubleone.mobilesearch.controller;

import org.springframework.web.bind.annotation.RestController;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.SessionBean;
import doubleone.mobilesearch.services.ScratchService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * ScratchController
 */
@RestController
public class ScratchController {

    
    @Autowired
    private MobileSearchProperties properties;
    @Autowired
    private ScratchService scratchService;

    @PostMapping(value="api/scratch")
    public String startScratch(@RequestParam("url") String url,@RequestParam("type")String type, HttpServletRequest request) {
        System.out.println(url + ": " + type);
        String message = "OK";
        String remoteHost = request.getRemoteHost();
        if(remoteHost.equals(request.getRemoteHost()))
            scratchService.scratch(url, type);
        else{
            message = "你没有权限";
        }
        System.out.println("{\"message\": \"" + message + "\"}");
        return "{\"message\": \"" + message + "\"}";
    }

    @DeleteMapping(value="api/scratch")
    public String stopScratch(SessionBean sessionBean) {
        System.out.println("stopScratch");
        return "{\"message\": \"it's OK\"}";
    }

    @GetMapping(value="api/scratch")
    public String getMethodName(@RequestParam String param) {
        return null;
    }
    
    @GetMapping(value="api/scratchtypes")
    public List<String> getScratchTypes(){
        return properties.getScratch().getProcessors();
    }
    
}