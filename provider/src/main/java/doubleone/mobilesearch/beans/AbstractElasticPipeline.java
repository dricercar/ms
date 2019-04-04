package doubleone.mobilesearch.beans;

import java.io.IOException;
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
import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class AbstractElasticPipeline implements Pipeline{
    
    protected RestHighLevelClient client;
    protected AtomicInteger counter;

    protected MobileSearchProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(ZGCJsonElasticPipeline.class);

    /**
     * 索引Product
     * @param product
     */
    public void index(Product product){
        if(exists(product.getName())){
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
        }
    }

    /**
     * 如果Product的name已经存在则返回true
     * @param value
     * @return
     */
    public boolean exists(String value) {
        SearchRequest searchRequest = new SearchRequest(properties.getElastic().getIndex());
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name.keyword", value));
        builder.fetchSource(false);
        searchRequest.source(builder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if(searchResponse.status().getStatus() == 200){
                return searchResponse.getHits().getTotalHits() > 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    protected abstract void match(final Product product, String field, String content);
}