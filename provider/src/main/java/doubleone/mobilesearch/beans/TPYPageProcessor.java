package doubleone.mobilesearch.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class TPYPageProcessor implements PageProcessor {

    private final static Logger logger = LoggerFactory.getLogger(TPYPageProcessor.class);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000);
    
    @Override
    public void process(Page page) {
        logger.info("processing " + page.getUrl().get());
    }

    @Override
    public Site getSite() {
        return site;
    }

}