package doubleone.mobilesearch.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doubleone.mobilesearch.beans.SpiderHolder;
import doubleone.mobilesearch.entity.ProductSource;
import doubleone.mobilesearch.repository.ProductSourceRepository;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Spider.Status;

/**
 * 用于从网络上抓取手机产品数据
 * 
 * @author doubleone
 */
@Service
public class ScratchService{

    @Autowired
    private ProductSourceRepository psRepository;
    @Autowired
    private SpiderHolder spiderHolder;

    private Long lastAccessTime = Instant.now().toEpochMilli();
    public void scratch(String url, String type){
        System.out.println(spiderHolder.size());
        String current = spiderHolder.getCurrent();
        System.out.println(current);
        if(type.equals(current)){
            spiderHolder.get(current).addUrl(url);
            if(spiderHolder.get(current).getStatus().equals(Status.Stopped)){
                spiderHolder.get(current).start();
                System.out.println("has running");
            }
        }else{
            if(spiderHolder.get(current) != null){
                System.out.println("current is stopping");
                spiderHolder.get(current).stop();
                System.out.println("current has stopped");
            }
            spiderHolder.get(type).addUrl(url).start();
            spiderHolder.setCurrent(type);
            System.err.println("change spider");
        }
        lastAccessTime = Instant.now().toEpochMilli();
    }

    public void stopScratch(){
        spiderHolder.get(spiderHolder.getCurrent()).stop();
        System.out.println("Stop scratch");
    }

    public List<ProductSource> getScratched(){
        Long currentTime = Instant.now().toEpochMilli();
        System.out.println("lastAccessTiem: " + lastAccessTime + "  currentTime: " + currentTime);
        List<ProductSource> list = psRepository.findByCreateTimeBetween(lastAccessTime, currentTime);
        lastAccessTime = currentTime;
        return list;
        
    }

}