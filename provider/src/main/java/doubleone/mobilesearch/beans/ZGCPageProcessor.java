package doubleone.mobilesearch.beans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ZGCPageProcessor implements PageProcessor {

    private final static Logger logger = LoggerFactory.getLogger(TPYPageProcessor.class);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000);
    @Override
    public void process(Page page) {
        logger.info("processing " + page.getUrl().get());

        page.addTargetRequests(page.getHtml().links().regex("http://detail.zol.com.cn/\\w+/\\w+/param.shtml").all());
		page.putField("名称", page.getHtml().xpath("//div[@class='breadcrumb']/a[@target='_self']/text()"));
		page.putField("品牌", page.getHtml().xpath("//body/div/div[@class='breadcrumb']/a[@id='_j_breadcrumb']/text()").toString());
		page.putField("价格", page.getHtml().css(".goods-card__price span").xpath("/span/text()").get());
		page.putField("imgUrl", page.getHtml().xpath("//body/div/div[@class='side']/div/div/a/img/@src"));
			
		List<String> titleList = page.getHtml().xpath("//table/tbody/tr/th/span/text()").all();
		List<String> contentList = page.getHtml().xpath("//table/tbody/tr/td//span/allText()").all();

		for (int i = 0; i < titleList.size(); i++) {
			page.putField(titleList.get(i), contentList.get(i));
        }
        logger.info("processed");
    }

    @Override
    public Site getSite() {
        return site;
    }

}