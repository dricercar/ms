package doubleone.mobilesearch.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import doubleone.mobilesearch.beans.SpiderHolder;
import doubleone.mobilesearch.beans.TPYPageProcessor;
import doubleone.mobilesearch.beans.ZGCPageProcessor;
import doubleone.mobilesearch.webmagic.fix.HttpClientDownloader;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Configuration
public class MobileSearchConfigutation {

    @Autowired
    private MobileSearchProperties properties;
    
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
                spider = Spider.create(new ZGCPageProcessor());
            }else if(type.equals(TPYPageProcessor.class.getSimpleName())){
                spider = Spider.create(new TPYPageProcessor());
            }else{
                it.remove();
                continue;
            }
            spider.setDownloader(downloader).addPipeline(new FilePipeline(dataDir));
            holder.putSpider(type, spider);
        }
        return holder;
    }
}