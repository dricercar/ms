package doubleone.mobilesearch.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import doubleone.mobilesearch.beans.SpiderHolder;
import doubleone.mobilesearch.beans.TPYJsonElasticPipeline;
import doubleone.mobilesearch.beans.TPYPageProcessor;
import doubleone.mobilesearch.beans.ZGCJsonElasticPipeline;
import doubleone.mobilesearch.beans.ZGCPageProcessor;
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
    
    @Bean
    public SpiderHolder mobileSpiders(){
        SpiderHolder holder = new SpiderHolder();
        List<String> list = properties.getScratch().getProcessors();
        String dataDir = properties.getScratch().getDataDir();
        Iterator<String> it = properties.getScratch().getProcessors().iterator();
        while(it.hasNext()){
            String type = it.next();
            HttpClientDownloader downloader = new HttpClientDownloader();
            Spider spider = null;
            if(type.equals(ZGCPageProcessor.class.getSimpleName())){
                spider = Spider.create(new ZGCPageProcessor()).addPipeline(new ZGCJsonElasticPipeline(client, properties));
            }else if(type.equals(TPYPageProcessor.class.getSimpleName())){
                spider = Spider.create(new TPYPageProcessor()).addPipeline(new TPYJsonElasticPipeline(client, properties));
            }else{
                it.remove();
                continue;
            }
            spider.setDownloader(downloader);
            holder.putSpider(type, spider);
        }
        return holder;
    }

    // @Bean
    // @ConditionalOnMissingBean
    // public Pipeline pipeline(){
    //     return new FilePipeline(properties.getScratch().getDataDir());
    // }

}