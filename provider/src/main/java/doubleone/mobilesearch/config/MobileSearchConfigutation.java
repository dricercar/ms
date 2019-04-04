package doubleone.mobilesearch.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import doubleone.mobilesearch.beans.PersistProductSourcePipeline;
import doubleone.mobilesearch.beans.SpiderHolder;
import doubleone.mobilesearch.beans.TPYJsonElasticPipeline;
import doubleone.mobilesearch.beans.TPYPageProcessor;
import doubleone.mobilesearch.beans.ZGCJsonElasticPipeline;
import doubleone.mobilesearch.beans.ZGCPageProcessor;
import doubleone.mobilesearch.entity.Product;
import doubleone.mobilesearch.webmagic.fix.HttpClientDownloader;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Configuration
public class MobileSearchConfigutation {

    @Autowired
    private MobileSearchProperties properties;
    @Autowired
    private RestHighLevelClient client;
    private Logger logger = LoggerFactory.getLogger(MobileSearchConfigutation.class);
    
    @Bean
    public SpiderHolder mobileSpiders(Pipeline pipeline, AtomicInteger counter) throws IOException {
        SpiderHolder holder = new SpiderHolder();
        Iterator<String> it = properties.getScratch().getProcessors().iterator();
        while(it.hasNext()){
            String type = it.next();
            HttpClientDownloader downloader = new HttpClientDownloader();
            Spider spider = null;
            if(type.equals(ZGCPageProcessor.class.getSimpleName())){
                spider = Spider.create(new ZGCPageProcessor()).addPipeline(new ZGCJsonElasticPipeline(client, properties, counter));
            }else if(type.equals(TPYPageProcessor.class.getSimpleName())){
                spider = Spider.create(new TPYPageProcessor()).addPipeline(new TPYJsonElasticPipeline(client, properties, counter));
            }else{
                it.remove();
                continue;
            }
            spider.setDownloader(downloader).addPipeline(pipeline);
            holder.putSpider(type, spider);
        }
        return holder;
    }

    @Bean
    public Pipeline pipeline(){
        return new PersistProductSourcePipeline();
    }

    /**
     * 每次初始化context时，从ElasticSearch中获取product的最大id
     * @return
     * @throws IOException
     */
    @Bean
    @ConditionalOnBean(value = RestHighLevelClient.class)
    public AtomicInteger counter() throws IOException {
        int max = maxId();
        AtomicInteger counter = new AtomicInteger();
        counter.set(max);
        return counter;
    }
    private int maxId() throws IOException{
        SearchRequest request = new SearchRequest(properties.getElastic().getIndex());
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.fetchSource(false);
        MaxAggregationBuilder aggregation = AggregationBuilders.max("max_id").field("id");
        builder.aggregation(aggregation);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        int max = 0;
        if(response.status().getStatus() == 200){
            ParsedMax parseMax = response.getAggregations().get("max_id");
            max = new Double(parseMax.getValue()).intValue();
        }
        logger.info("max id: " + max);
        return max;
    }
}