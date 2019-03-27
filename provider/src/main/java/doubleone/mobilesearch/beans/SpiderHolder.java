package doubleone.mobilesearch.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import us.codecraft.webmagic.Spider;

public class SpiderHolder{
    private String current;
    private Map<String, Spider> maps;
    public SpiderHolder(){
        maps = new HashMap<>();
    }

    public void putSpider(String type,Spider spider){
        this.maps.put(type, spider);
    }
    public Spider get(String type){
        return this.maps.get(type);
    }
    public String getCurrent(){
        return current;
    }
    public void setCurrent(String current){
        this.current = current;
    }
    public void stop(){
        Optional<Spider> optional = Optional.of(this.maps.get(current));
        Spider spider = optional.get();
        spider.stop();
    }
    public void start(){
        Optional<Spider> optional = Optional.of(this.maps.get(current));
        Spider spider = optional.get();
        spider.start();
    }
    public void addUrl(String url){
        Optional<Spider> optional = Optional.of(this.maps.get(current));
        Spider spider = optional.get();
        spider.addUrl(url);
    }
}