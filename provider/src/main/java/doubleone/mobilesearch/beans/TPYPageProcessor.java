package doubleone.mobilesearch.beans;

import java.util.List;

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
        page.addTargetRequests(page.getHtml().links().regex("https://product.pconline.com.cn/mobile/\\w+/\\w+_detail.html").all());

        String name = page.getHtml().xpath("//div[@class='area area-coreparams']/div[@class='box']/div[@class='hd']/h3/text()").get();
        String brand = page.getHtml().xpath("//div[@class='wraper']/div[@class='crumb']/a[4]/text()").get();
        String price = page.getHtml().xpath("//div[@class='box productinfo']/div[@class='bd']/p/a[@class='r-price fc-red']/allText()").get();
        String imgUrl = "https:" + page.getHtml().xpath("//div[@class='box productinfo']/div[@class='bd']/a/img/@#src").get();
        // brand = brand.substring(0, brand.indexOf("手机大全"));
        if(brand != null && brand.indexOf("手机大全") > 0){
            brand = brand.substring(0, brand.indexOf("手机大全"));
        }
        page.putField("名称", name);
		page.putField("品牌", brand);
		page.putField("价格", price);
        page.putField("imgUrl", imgUrl);
        //详细数据
        List<String> tables = page.getHtml().xpath("//div[@class='area area-detailparams']/div[@class='box']/div[@class='bd']/table").all();
        System.out.println(tables.size());
        for(int i = 1; i < tables.size(); i++) {
        	String id="Jbasicparams"+i;
        	List<String> bodys = page.getHtml().xpath("//table[@id='"+id+"']/tbody/tr").all();
        	for(int j = 0; j <= bodys.size(); j++) {
        		String paras = page.getHtml().xpath("//table[@id='"+id+"']/tbody/tr["+j+"]/th/text()").get();
        		String value = page.getHtml().xpath("//table[@id='"+id+"']/tbody/tr["+j+"]/td/allText()").get();
        		page.putField(paras, value);
        	}
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}