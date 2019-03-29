package doubleone.mobilesearch.beans;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.alibaba.fastjson.JSON;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import doubleone.mobilesearch.config.MobileSearchProperties;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.entity.ZGCContent;
import doubleone.mobilesearch.util.NoiseReducer;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class ZGCJsonElasticPipeline implements Pipeline {

    // @Autowired
    private RestHighLevelClient client;

    // @Autowired
    private MobileSearchProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(ZGCJsonElasticPipeline.class);

    public ZGCJsonElasticPipeline(){

    }
    public ZGCJsonElasticPipeline(RestHighLevelClient client, MobileSearchProperties properties){
        this.client = client;
        this.properties = properties;
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
            String text = JSON.toJSONString(product);
            IndexRequest request = new IndexRequest(properties.getElastic().getIndex().toLowerCase(), "product");
            request.source(text, XContentType.JSON);
            try {
                client.index(request, RequestOptions.DEFAULT); 
                logger.info("indexed " + product.getName());
            } catch (IOException e) {
                logger.info("indexing exception" + e.getMessage());
                e.printStackTrace();
            }
        }
        
    }

    private void match(final Product product, String field, String content){
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
}