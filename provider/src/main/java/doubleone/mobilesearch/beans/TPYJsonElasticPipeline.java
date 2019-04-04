package doubleone.mobilesearch.beans;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.TPYContent;
import doubleone.mobilesearch.util.NoiseReducer;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class TPYJsonElasticPipeline extends AbstractElasticPipeline {

    // @Autowired
    // private RestHighLevelClient client;
    // private AtomicInteger counter;

    // @Autowired
    // private MobileSearchProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(ZGCJsonElasticPipeline.class);

    public TPYJsonElasticPipeline(){

    }
    public TPYJsonElasticPipeline(RestHighLevelClient client, MobileSearchProperties properties, AtomicInteger counter){
        this.client = client;
        this.properties = properties;
        this.counter = counter;
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        Product product = new Product();
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            String field = entry.getKey();
            StringBuilder values = new StringBuilder();
            if (entry.getValue() instanceof Iterable) {
                Iterable value = (Iterable) entry.getValue();
                for (Object o : value) {
                    values.append(o);
                }
            } else {
                values.append(entry.getValue());
            }
            String content = values.toString();
            if(field != null)
                match(product, field, content);
        }
        if(product.getName() != null && !product.getName().contains("null")){
            // String text = JSON.toJSONString(product);
            // IndexRequest request = new IndexRequest(properties.getElastic().getIndex().toLowerCase(), "product");
            // request.source(text, XContentType.JSON);
            // try {
            //     client.index(request, RequestOptions.DEFAULT); 
            //     logger.info("indexed " + product.getName());
            // } catch (IOException e) {
            //     logger.info("indexing exception" + e.getMessage());
            //     e.printStackTrace();
            // }
            index(product);
        }
    }
    protected void match(final Product product, String field, String content){
        content = NoiseReducer.reduceNoise(content, '•', ',');
        Optional<String> optional = Optional.empty();
            if(content.contains("null")) {
                optional = Optional.ofNullable(null);
            }else {
                optional = Optional.of(content.trim());
            }
            switch(field) {
                case TPYContent.BRAND:
                    product.setBrand(optional.orElse(null));
                    break;
                case TPYContent.NAME:
                    product.setName(optional.orElse(null));
                    break;
                case TPYContent.PRICE:
                    product.setPrice(optional.orElse(null));
                    break;
                case TPYContent.TYPE:
                    product.setType(optional.orElse(null));
                    break;
                case TPYContent.SIZE:
                    product.setSize(optional.orElse(null));
                    break;
                case TPYContent.OS:
                    product.setOs(optional.orElse(null));
                    break;
                case TPYContent.IMAGEURL:
                    product.setImgUrl(optional.orElse(null));
                    break;
                case TPYContent.CPU:
                    product.setCpu(optional.orElse(null));
                    break;
            }
    }
    // public void index(Product product){
    //     if(exists(product.getName()))
    //         return;
    //     product.setId((long)counter.incrementAndGet());
    //     String text = JSON.toJSONString(product);
    //     IndexRequest request = new IndexRequest(properties.getElastic().getIndex().toLowerCase(), "product");
    //     request.source(text, XContentType.JSON);
    //     try {
    //         client.index(request, RequestOptions.DEFAULT); 
    //         logger.info("indexed " + product.getName());
    //     } catch (IOException e) {
    //         logger.info("indexing exception" + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }

    // public boolean exists(String value) {
    //     SearchRequest searchRequest = new SearchRequest(properties.getElastic().getIndex());
    //     SearchSourceBuilder builder = new SearchSourceBuilder();
    //     builder.query(QueryBuilders.termQuery("name.keyword", value));
    //     builder.fetchSource(false);
    //     searchRequest.source(builder);
    //     SearchResponse searchResponse;
    //     try {
    //         searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
    //         if(searchResponse.status().getStatus() == 200){
    //             return searchResponse.getHits().getTotalHits() > 0;
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    //     return false;
    // }
}