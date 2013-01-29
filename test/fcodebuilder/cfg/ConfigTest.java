/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.cfg;

import fcodebuilder.cfg.Config.SysConfig;
import fcodebuilder.cfg.Config.UseConfig;
import fcodebuilder.match.TDMatch;
import java.util.Map;
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
public class ConfigTest {
    
    public ConfigTest() {
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
     * Test of wildcard method, of class Config.
     */
    /**
     * Test of wildcard method, of class TDMatch.
     */
    @Test
    public void testWildcard() {
        System.out.println("wildcard");
        String s = "";
        Config instance = Config.getConfig();
        String expResult = "";
        String result = instance.wildcard("",s);
    }
}
