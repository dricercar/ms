package doubleone.mobilesearch.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import doubleone.mobilesearch.entity.ProductSource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ProductSourceRepositoryTest{
    @Autowired
    private ProductSourceRepository repository;

    @Test
    public void testCreate(){
        assertNotNull(repository);
    }

    @Test
    public void testC(){
        System.out.println("testC");
        ProductSource ps = new ProductSource("苹果", "http://www.baidu.com");
        repository.save(ps);
        Optional<ProductSource> expect = repository.findById(1);
        if(expect.isPresent())
            assertEquals(ps, expect.isPresent());
    }
}