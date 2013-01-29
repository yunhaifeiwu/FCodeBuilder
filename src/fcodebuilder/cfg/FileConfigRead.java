/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.cfg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nutz.json.Json;

/**
 *
 * @author cloud
 */
public class FileConfigRead extends ConfigControl {
    public FileConfigRead(){}

    public FileConfigRead(String file) {
        super(file);
    }
     
    public void saveDefault(String fileName) throws FileNotFoundException, UnsupportedEncodingException, IOException{
       
        BufferedWriter bufWriter = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(fileName,false), "UTF-8"
            )
        );  
               
        bufWriter.write("/* \r\n");     
        bufWriter.write(" inCachePath;输入缓存地址（模板及 模板数据的输入）\r\n");
        bufWriter.write(" outeCachePath; 输出缓存地址\r\n");
        
        bufWriter.write(" inTemplateType;输入模板类型？\r\n");
        bufWriter.write(" inTemplateAdress; 模板地址\r\n");
        bufWriter.write(" ignoreInTemplatePath;忽略模板地址中某些路径\r\n");  
        
        bufWriter.write(" inTemplateDataType; 输出模板类型？\r\n");
        bufWriter.write(" inTemplateDateAdress;  模板数据地址\r\n");
        bufWriter.write(" ignoreInTemplateDataPath; 忽略模板数据地址中某些路径\r\n");
        
        bufWriter.write(" outType;输出类型\r\n");
        bufWriter.write(" outAdress; 输出地址\r\n");
        bufWriter.write(" ignoreOutPath; 忽加输出地址中某些路径\r\n");
        bufWriter.write(" */\r\n");
        bufWriter.write("{\r\n");     
        bufWriter.write("  inCachePath:'data/cache/in',\r\n") ;
        bufWriter.write("  outCachePath:'data/cache/out',\r\n") ;
        
        bufWriter.write("  inTemplateType:'file', \r\n") ;
        bufWriter.write("  inTemplateAdress:'data/template',  \r\n");
        bufWriter.write("  ignoreInTemplatePath:null,  \r\n");  
        
        bufWriter.write("  inTemplateDataType:'file', \r\n");
        bufWriter.write("  inTemplateDateAdress:'data/templateData',  \r\n");
        bufWriter.write("  ignoreInTemplateDataPath:null, \r\n");        
        
        bufWriter.write("  outType:'file', \r\n");
        bufWriter.write("  outAdress:'data/out', \r\n");
        bufWriter.write("  ignoreOutPath:null, \r\n");
        
        bufWriter.write("  extends:{ \r\n");
        bufWriter.write("  }, \r\n");
        
        bufWriter.write("  rules:[ \r\n");
        bufWriter.write("     { \r\n");
        bufWriter.write("       templateData:'data/templateData/*', \r\n");
        bufWriter.write("       template:'data/template/*', \r\n");
        bufWriter.write("       igore:'false', \r\n");
        bufWriter.write("       out:'data/out', \r\n");
        bufWriter.write("       disableOut:'false' \r\n");
        bufWriter.write("     }, \r\n");
        bufWriter.write("     { \r\n");
        bufWriter.write("       templateData:'xx', \r\n");
        bufWriter.write("       template:'xx', \r\n");
        bufWriter.write("       out:'xx', \r\n");
        bufWriter.write("       igore:'false' \r\n");
        bufWriter.write("     } \r\n");
        bufWriter.write("  ]\r\n");
        
        
        
        bufWriter.write("}\r\n");
        
        bufWriter.close();  


    }
    
    @Override
    public Config getConfigImpl(String fileName) {
        Config cfg=Config.getConfig();
        
        File f=new File(fileName);
        if(!f.exists()){//如果指定的文件不存在，生成默认配置
            try {
                saveDefault(fileName);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileConfigRead.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(FileConfigRead.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileConfigRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        InputStreamReader is = null;
        try {//打开输入文件流
            try {
                is = new InputStreamReader( new FileInputStream(fileName),
                   "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(FileConfigRead.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileConfigRead.class.getName()).log(Level.SEVERE, null, ex);
        }
           

        Map map=(Map) Json.fromJson(is);
        try {//关闭输入文件流
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(FileConfigRead.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Config.userConfigFromMap(map);
   
 
        
   
    
        System.out.println();
        
        return cfg;
    }
    
}
