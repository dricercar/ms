package doubleone.mobilesearch.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import doubleone.mobilesearch.beans.SpiderHolder;

@RunWith(SpringRunner.class)
public class MobileSearchConfigurationTest{
    
    @Autowired
    SpiderHolder spiderHolder;

    @Test
    public void testCreate(){
        System.out.println("testCreate");
        assertNotNull(spiderHolder);
        assertEquals(2, spiderHolder.size());
    }
}