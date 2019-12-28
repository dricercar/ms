package doubleone.mobilesearch.controller;

import org.springframework.web.bind.annotation.RestController;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.Payload;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.ProductSource;
import doubleone.mobilesearch.exception.PermissionDeniedException;
import doubleone.mobilesearch.services.ScratchService;
import io.swagger.annotations.Api;
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
@Api("爬虫管理")
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
    public Payload<Void> startScratch(@RequestParam("url") String url,@RequestParam("type")String type, HttpServletRequest request) {
        System.out.println(url + ": " + type);
        String remoteHost = request.getRemoteHost();
        if(remoteHost.equals(request.getRemoteHost()))
            scratchService.scratch(url, type);
        else{
            throw new PermissionDeniedException("401", "你没有权限使用该功能");
        }
        return new Payload<>();
    }

    @ApiOperation(value="停止爬取数据")
    @DeleteMapping(value="api/scratch")
    public Payload<Void> stopScratch() {
        System.out.println("stopScratch");
        scratchService.stopScratch();
        return new Payload<>();
    }

    @ApiOperation(value="请求已爬取的手机数据")
    @GetMapping(value="api/scratch")
    public Payload<List<ProductSource>> getScratched() {
        // List<ProductSource> list = scratchService.getScratched();
        // list.forEach(item -> System.out.println(item.getName()));
        // System.out.println(list);
        return new Payload<List<ProductSource>>(scratchService.getScratched());
    }
    
    @ApiOperation(value="可使用的Processor类型")
    @GetMapping(value="api/scratchtypes")
    public List<String> getScratchTypes(){
        return properties.getScratch().getProcessors();
    }
    
}