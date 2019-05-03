package doubleone.mobilesearch.beans;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.ZGCContent;
import doubleone.mobilesearch.util.NoiseReducer;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class ZGCJsonElasticPipeline extends AbstractElasticPipeline {

    // @Autowired
    // private RestHighLevelClient client;

    // @Autowired
    // private MobileSearchProperties properties;
    // private AtomicInteger counter;
    private static final Logger logger = LoggerFactory.getLogger(ZGCJsonElasticPipeline.class);

    public ZGCJsonElasticPipeline(){

    }
    public ZGCJsonElasticPipeline(RestHighLevelClient client, MobileSearchProperties properties, AtomicInteger counter){
        this.client = client;
        this.properties = properties;
        this.counter = counter;
    }
    @Override
    public void process(ResultItems resultItems, Task task){
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

    @Override
    protected void match(final Product product, String field, String content){
        content = NoiseReducer.reduceNoise(content, '>', '，');
        Optional<String> optional = Optional.empty();
            if(content.contains("null")) {
                optional = Optional.ofNullable(null);
            }else {
                optional = Optional.of(content.trim());
            }
            
            switch(field) {
                case ZGCContent.BRAND:
                    product.setBrand(optional.orElse(null));
                    break;
                case ZGCContent.NAME:
                    product.setName(optional.orElse(null));
                    break;
                case ZGCContent.PRICE:
                    product.setPrice(optional.orElse(null));
                    break;
                case ZGCContent.TYPE:
                    product.setType(optional.orElse(null));
                    break;
                case ZGCContent.SIZE:
                    product.setSize(optional.orElse(null));
                    break;
                case ZGCContent.OS:
                    product.setOs(optional.orElse(null));
                    break;
                case ZGCContent.IMAGEURL:
                    product.setImgUrl(optional.orElse(null));
                    break;
                case ZGCContent.CPU:
                    product.setCpu(optional.orElse(null));
                    break;
            }
    }
    // public void index(Product product){
    //     if(exists(product.getName())){
    //         logger.warn(product.getName() + " has existed");
    //         return;
    //     }
    //     product.setId((long)counter.incrementAndGet());
    //     String text = JSON.toJSONString(product);
    //     IndexRequest request = new IndexRequest(properties.getElastic().getIndex().toLowerCase(), "product");
    //     request.source(text, XContentType.JSON);
    //     try {
    //         client.index(request, RequestOptions.DEFAULT); 
    //         logger.info("indexed " + product.getName());
    //     } catch (IOException e) {
    //         logger.error("indexing exception" + e.getMessage());
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