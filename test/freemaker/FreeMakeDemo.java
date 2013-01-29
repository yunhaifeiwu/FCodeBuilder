/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package freemaker;

import fcodebuilder.cfg.FileConfigRead;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import org.junit.Test;
import org.nutz.ioc.Ioc;
import org.nutz.json.Json;

public class FreeMakeDemo {
    public static Ioc ioc;
    @Test
    public void test1() throws FileNotFoundException,
        UnsupportedEncodingException, IOException, TemplateException
    {
 
        //---------------读json文件 到map中 ----得到 数据源---begin
        InputStreamReader is = new InputStreamReader(
                new FileInputStream("test/freemaker/data.json"), "UTF-8"
        );
        Map map=(Map) Json.fromJson(is);
        is.close();
        //---------------读json文件 到map中 ----得到 数据源---------end        
  
        Configuration cfg = new Configuration();  
        cfg.setEncoding(Locale.getDefault(),"UTF-8");
        // 指定模板所在的基本目录   
        cfg.setDirectoryForTemplateLoading(  new File("test/freemaker")   );   
        Template template = cfg.getTemplate("05.html.ftl");//加载模板文件

        File htmlFile = new File("test/freemaker/aa.html");
        Writer out = new BufferedWriter(new OutputStreamWriter(
           new FileOutputStream(htmlFile), "UTF-8"));//输出文件
        template.process(map, out);
        
        System.out.println();
    }
}
