package doubleone.mobilesearch.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.ProductPage;
import doubleone.mobilesearch.exception.NotFoundException;

@Service
public class SearchService {

    private static final int COUNT_PER_PAGE = 2;
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private MobileSearchProperties properties;

    public ProductPage search(String query, int page) throws IOException {
        logger.info("query: " + query + " page: " + page);
        SearchRequest request = new SearchRequest(properties.getElastic().getIndex());
        SearchSourceBuilder builder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "name", "brand", "type", "os");
        builder.query(queryBuilder);
        if(page != 1)
            builder.size(COUNT_PER_PAGE).from((page-1)*COUNT_PER_PAGE);
        builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        List<Product> list = new LinkedList<>();
        Integer totalPage = null;;
        Integer currentPage = page;
        if(response.status().getStatus() == 200){
            SearchHits hits = response.getHits();
            if(page == 1){
                totalPage = new Double(Math.ceil(hits.getTotalHits()/COUNT_PER_PAGE)).intValue();
            }
            int i = 0;
            for(SearchHit hit: hits){
                if(i < COUNT_PER_PAGE){
                    Product p = JSON.parseObject(hit.getSourceAsString(), Product.class);
                    System.out.println("product: " + p);
                    list.add(p);
                    i++;
                }
            }
        }
        return new ProductPage(totalPage, currentPage, list);
    }

    public Product findProduct(int id)  {
        SearchRequest request = new SearchRequest(properties.getElastic().getIndex().toLowerCase());
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("id", id));
        builder.size(1);
    
        request.source(builder);
        
        SearchResponse response;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
            if(response.status().getStatus() == 200){
                if(response.getHits().getTotalHits() > 0){
                    String text = response.getHits().getHits()[0].getSourceAsString();
                    return JSON.parseObject(text, Product.class);
                }else{
                    throw new NotFoundException("404", "没有找到该产品");
                }
            }
        } catch (IOException e) {
            throw new NotFoundException("404", "没有找到该产品");
        }
        return null;

    }
    // public List<Product> search(String query, int page) throws IOException {
    //     SearchRequest request = new SearchRequest(properties.getElastic().getIndex());
    //     SearchSourceBuilder builder = new SearchSourceBuilder();
    //     QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, "名称", "品牌", "type", "os");
    //     builder.query(queryBuilder);
    //     builder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
    //     request.source(builder);
    //     SearchResponse response = client.search(request, RequestOptions.DEFAULT);
    //     List<Product> list = new LinkedList<>();
    //     if(response.status().getStatus() == 200){
    //         SearchHits hits = response.getHits();
    //         for(SearchHit hit: hits){
    //             System.out.println("hit: " + hit);
    //             Product p = JSON.parseObject(hit.getSourceAsString(), Product.class);
    //             System.out.println("product: " + p);
    //             list.add(p);
    //         }
    //     }
    //     return list;
    // }
    
}