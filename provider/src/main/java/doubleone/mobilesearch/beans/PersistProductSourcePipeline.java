package doubleone.mobilesearch.beans;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import doubleone.mobilesearch.entity.ProductSource;
import doubleone.mobilesearch.repository.ProductSourceRepository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class PersistProductSourcePipeline implements Pipeline {

    @Autowired
    private ProductSourceRepository repository;
    private Logger logger = LoggerFactory.getLogger(PersistProductSourcePipeline.class);
    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            String field = entry.getKey();
            if(field.equals("名称") && !entry.getValue().toString().equals("null")){
                ProductSource ps = new ProductSource(entry.getValue().toString(), resultItems.getRequest().getUrl(), Instant.now().toEpochMilli());
                repository.save(ps);
                logger.info("persist productsource:" + ps.getName() + " -- " + ps.getCreateTime());
                break;
            }
        }
    }

}