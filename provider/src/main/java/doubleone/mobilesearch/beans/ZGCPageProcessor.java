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
        String mobile = page.getHtml().xpath("//body/div/div[@class='breadcrumb']/a[2]/text()").get();
        logger.info(mobile);
        if(mobile != null && !mobile.equals("手机"))
            return;
        String name = page.getHtml().xpath("//div[@class='breadcrumb']/a[@target='_self']/text()").toString();
        String brand = page.getHtml().xpath("//body/div/div[@class='breadcrumb']/a[@id='_j_breadcrumb']/text()").toString();
        String price = page.getHtml().xpath("//body/div/div[@class='side']/div/div/a/span[@class='red']/text()").toString();
        String imgUrl = page.getHtml().xpath("//body/div/div[@class='side']/div/div/a/img/@src").toString();
		page.putField("名称", name);
		page.putField("品牌", brand);
        page.putField("价格", price);
        page.putField("imgUrl", imgUrl);
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