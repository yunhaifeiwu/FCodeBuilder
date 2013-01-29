/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.in;

import java.util.List;
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
public class ReadSourceFromFileTest {
    
    public ReadSourceFromFileTest() {
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
     * 测试文件夹不存在
     */
    @Test
    public void testGetFileInfosImpl() {
        System.out.println("测试文件夹不存在情况开始。");
        
        ReadSourceFromFile instance = new ReadSourceFromFile();
        List expResult = null;
        List<FileInfo> result = instance.getFileInfosImpl();
         System.out.println("显示返回结果：");
        for(FileInfo fn:result){
             System.out.println(fn.getName());
        }
        System.out.println("测试成功！");
 
//        fail("The test case is a prototype.");
    }
}
