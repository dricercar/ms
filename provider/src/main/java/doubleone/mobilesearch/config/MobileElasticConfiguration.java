package doubleone.mobilesearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import doubleone.mobilesearch.beans.ZGCJsonElasticPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

@Configuration
public class MobileElasticConfiguration{
    
    @Autowired
    private MobileSearchProperties properties;
    
    @Bean(destroyMethod = "close")
    public RestHighLevelClient highLevelClient(){
        String host = properties.getElastic().getHost();
        int port = properties.getElastic().getPort();
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
    }

    @Bean
    public Pipeline pipeline(){
        return new ZGCJsonElasticPipeline();
    }

}