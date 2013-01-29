/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.match;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cloud
 */
public class MatchNotifyTest {
    
    public MatchNotifyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * 测试 事件关联属性。当投递一个事件后，AcceptTest类响应该事件。并修改值。
     * 如果本测试中收到预定的修改值，测试通过。
     */
    @Test
    public void testPostMatchData() {
        System.out.println("postMatchData");
         Pattern p =   Pattern.compile("a");
          
        Matcher m = p.matcher("ddabv");
        m.find();
       
        MatchData md = new MatchData();
        md.setTemplate("data/aa/${ddd}");
        Map<String,Object> td=new HashMap<>();
        td.put("ddd", "ddd1"); 
        md.setTemplateData(td);
        MatchNotify instance = new MatchNotify();
        AcceptTest ac=new AcceptTest(instance);
        instance.postMatchData(md);
        assertEquals("AcceptTest",td.get("ddd"));
        
    }
    
      
    
}
