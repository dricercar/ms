package doubleone.mobilesearch.controller;

import org.springframework.web.bind.annotation.RestController;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.SessionBean;
import doubleone.mobilesearch.services.ScratchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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

    /**
     * 
     * @param url 要爬取的种子URL
     * @param type  Processor类型
     * @param request
     * @return
     */
    @ApiOperation(value="爬取指定URL的手机数据", notes="")
    @ApiImplicitParams({
        @ApiImplicitParam(name="url", value="指定URL", required=true, dataType = "String" ),
        @ApiImplicitParam(name="type", value="Processor类型", required=true)
    })
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

    @ApiOperation(value="停止爬取数据")
    @DeleteMapping(value="api/scratch")
    public String stopScratch() {
        System.out.println("stopScratch");
        return "{\"message\": \"it's OK\"}";
    }

    @ApiOperation(value="请求已爬取的手机数据")
    @GetMapping(value="api/scratch")
    public String getMethodName(@RequestParam String param) {
        return null;
    }
    
    @ApiOperation(value="可使用的Processor类型")
    @GetMapping(value="api/scratchtypes")
    public List<String> getScratchTypes(){
        return properties.getScratch().getProcessors();
    }
    
}