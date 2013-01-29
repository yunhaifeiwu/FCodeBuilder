/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.out;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cloud
 */
public class OutToSVNTest {
    
    public OutToSVNTest() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testAction() {
        System.out.println("action");
        String outPath = "";
        OutToSVN instance = new OutToSVN();
        instance.action(outPath);
    }
}
