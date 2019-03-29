package doubleone.mobilesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import doubleone.mobilesearch.beans.SpiderHolder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
    SpiderHolder spiderHolder;

    @Test
    public void testCreate(){
        System.out.println("testCreate");
        assertNotNull(spiderHolder);
        assertEquals(2, spiderHolder.size());
    }

}
