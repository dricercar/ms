package doubleone.mobilesearch.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import doubleone.mobilesearch.entity.Payload;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.services.SubmitService;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubmitController{
    @Autowired
    private SubmitService submitService;

    private final String IMGURL = "images/";
     
    @ApiOperation(value="提交手机产品数据")
    @RequestMapping(value="api/addProduct", method=RequestMethod.POST)
    @ResponseBody
    public Payload<Void> addProduct(@RequestParam("name")String name, @RequestParam("brand")String brand, @RequestParam("price")String price,
            @RequestPart("img")MultipartFile file ,@RequestParam(value="type")String type, @RequestParam("os")String os, @RequestParam("cpu")String cpu,
            @RequestParam("size")String size, HttpServletRequest request) throws IllegalStateException, IOException {
        
        String path = request.getSession().getServletContext().getRealPath("images/");
        System.out.println(path);
        File f = new File(path);
        if(!f.exists())
            f.mkdir();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "")+suffix;
        System.out.println(path+fileName);
        file.transferTo(new File(path+fileName));
        String imgUrl = IMGURL + fileName;
        Product product = new Product(name, brand, price, imgUrl , type, os, cpu, size);
        submitService.submit(product);
        return new Payload<Void>();
    }
    
}
