/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.svn;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 *
 * @author cloud
 */
public class SvnUtilTest {
    
    public SvnUtilTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    
    @Test
    public void testDoCheckOut() {
        System.out.println("doCheckOut");
        String checkOutDir = "E:/fyh/git/FCodeBuilder/data/out";
        SvnUtil instance = new SvnUtil();
        instance.setAddress("https://mis/svn/out/");
        instance.setUsername("admin");
        instance.setPassword("123");
        instance.doCheckOut(checkOutDir);
        
        
    }
}
