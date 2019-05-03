package doubleone.mobilesearch.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doubleone.mobilesearch.entity.Payload;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.ProductPage;
import doubleone.mobilesearch.services.SearchService;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("api/search")
    public Payload<ProductPage> search(@RequestParam("wd") String query, @RequestParam(value = "page", defaultValue = "1")int page) throws IOException {
        return new Payload<ProductPage>(searchService.search(query, page));
    }
    @GetMapping("api/detail")
    public Payload<Product> findProduct(@RequestParam("id")int id){
        return new Payload<Product>(searchService.findProduct(id));
    }
}