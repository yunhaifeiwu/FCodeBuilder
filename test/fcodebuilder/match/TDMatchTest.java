/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.match;

import fcodebuilder.in.ReadSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.nutz.json.Json;

/**
 *
 * @author cloud
 */
public class TDMatchTest extends TDMatch {
    
    public TDMatchTest() {
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
     * Test of action method, of class TDMatch.
     */
//    @Test
    public void testAction() {
//        Pattern p=Pattern.compile("{");
//        Matcher m=p.matcher("sss{}ada");
//        System.out.println("action");
        String nickname= "";
		System.out.println("nickname:@@@@@@@@@@@@@@@@"+nickname.trim()+"ddd");
        TDMatch instance = new TDMatch();
        instance.action();
    }
    
    @Test
    public void getValueFromLink_Map_Test() throws UnsupportedEncodingException, FileNotFoundException, IOException{
        System.out.println("getValueFromLink_Map_Test");
        InputStreamReader is = new InputStreamReader(
                new FileInputStream("test/fcodebuilder/match/data.json"), "UTF-8"
        );
        Map<String, Object> map=(Map) Json.fromJson(is);
        is.close();
        LinkedList<TDMatch.MKey> list=new LinkedList<>();
        TDMatch.MKey mk=new TDMatch.MKey();
        mk.key="users";
        mk.pos=1;
        list.add(mk);
        mk=new TDMatch.MKey();
        mk.key="username";
        list.add(mk);
        TDMatch instance = new TDMatch();
        
        String dd=instance.getValueFromLink_Map(list,map);        
        System.out.println(dd);
        System.out.println();
        assertEquals("李四",dd);
        
        list.clear();
        mk=new TDMatch.MKey();
        mk.key="computer";
        mk.pos=1;
        list.add(mk);
        mk=new TDMatch.MKey();
        mk.key="desk1";
        mk.pos=0;
        list.add(mk);
        mk=new TDMatch.MKey();
        mk.key="name";
        list.add(mk);
        dd=instance.getValueFromLink_Map(list,map);        
        System.out.println(dd);
        System.out.println();
        assertEquals("张三",dd);  
 
        
    }
    
    @Test
    public void getPathFromWild_card_Test() throws UnsupportedEncodingException, FileNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        System.out.println("getPathFromWild_card_Test");
        TDMatch instance = new TDMatch();
        
        System.out.println("getValueFromLink_Map_Test");
        InputStreamReader is = new InputStreamReader(
                new FileInputStream("test/fcodebuilder/match/data.json"), "UTF-8"
        );
        Map<String, Object> map=(Map) Json.fromJson(is);
        is.close();
        
        Method m = instance .getClass().getDeclaredMethod("getPathFromWild_card",new Class[]{String.class,Map.class});
        m.setAccessible(true);
        Object result = m.invoke(instance ,new Object[] {new String("${usernamepath}"),map});       
        assertEquals("管理员path",result);  
        
        m = instance .getClass().getDeclaredMethod("getPathFromWild_card",new Class[]{String.class,Map.class});
        m.setAccessible(true);
        result = m.invoke(instance ,new Object[] {new String("${computer[0]}"),map});        
        assertEquals("基本数组元素",result);  
        
        m = instance .getClass().getDeclaredMethod("getPathFromWild_card",new Class[]{String.class,Map.class});
        m.setAccessible(true);
        result = m.invoke(instance ,new Object[] {new String("${computer[2].c.doc.desk1[0].name}"),map});        
        assertEquals("张三",result);  
        
    }
    
    @Test
    public void fileExtensionsChange_Test() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        System.out.println("fileExtensionsChange_Test");
        TDMatch instance = new TDMatch();
        Method m = instance .getClass().getDeclaredMethod("fileExtensionsChange",new Class[]{String.class});
        m.setAccessible(true);
        Object result = m.invoke(instance ,new Object[] {new String("ddd.txt.aa.ftl")});       
        assertEquals("ddd.txt.aa",result);  
        
    }
}
