package doubleone.mobilesearch.services;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.Product;

@RestController
public class SubmitService{
    @Autowired
    public RestHighLevelClient client;
    @Autowired
    private MobileSearchProperties properties;
    @Autowired
    private SearchService searchService;

    @Autowired
    private AtomicInteger counter;
    
    private static final Logger logger = LoggerFactory.getLogger(SubmitService.class);
    public String submit(Product product) throws IOException {
        String id = exists(product.getName()).orElse(null);
        if(id != null){
            update(product, id);
        }else{
            index(product);
        }
        return "OK";
    }

    private void index(Product product) throws IOException {
        if(exists(product.getName()).isPresent()){
            logger.warn(product.getName() + " has existed");
            return;
        }
        product.setId((long)counter.incrementAndGet());
        String text = JSON.toJSONString(product);
        IndexRequest request = new IndexRequest(properties.getElastic().getIndex().toLowerCase(), "product");
        request.source(text, XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT); 
            logger.info("indexed " + product.getName());
        } catch (IOException e) {
            logger.error("indexing exception" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    private void update(Product product, String id) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(properties.getElastic().getIndex().toLowerCase(), "product", id);
        String text = JSON.toJSONString(product);
        updateRequest.doc(text, XContentType.JSON);

        client.update(updateRequest, RequestOptions.DEFAULT);
        
    }

    /**
     * 如果Product的name已经存在则返回true
     * 
     * @param value
     * @return
     * @throws IOException
     */
    private Optional<String> exists(String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(properties.getElastic().getIndex());
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name.keyword", value));
        builder.fetchSource(false);
        searchRequest.source(builder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if(searchResponse.status().getStatus() == 200 && searchResponse.getHits().getTotalHits() > 0){
                Optional.ofNullable(searchResponse.getHits().getAt(0).getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return Optional.ofNullable(null);
    }
}