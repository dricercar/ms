package doubleone.mobilesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import doubleone.mobilesearch.beans.SpiderHolder;
import doubleone.mobilesearch.entity.ProductSource;
import doubleone.mobilesearch.repository.ProductSourceRepository;
import doubleone.mobilesearch.repository.ProductSourceRepositoryTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    ProductSourceRepository repository;

    @Test
    public void testCreate(){
        System.out.println("testCreate");
        ProductSource ps = new ProductSource("苹果", "http://www.baidu.com");
        repository.save(ps);
        Optional<ProductSource> expect = repository.findById(1);
        if(expect.isPresent())
            assertEquals(ps, expect.isPresent());
    }

}
