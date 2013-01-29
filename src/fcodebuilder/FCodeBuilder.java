/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder;

 
import fcodebuilder.cfg.Config.SysConfig;
import fcodebuilder.cfg.ConfigControl;
import fcodebuilder.cfg.FileConfigRead;
import fcodebuilder.engine.CodeEngine;
import fcodebuilder.engine.FreeMakerEngine;
import fcodebuilder.in.ReadSource;
import fcodebuilder.match.MatchNotify;
import fcodebuilder.match.TDMatch;
import fcodebuilder.out.OutControl;
import fcodebuilder.out.OutNotify;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

/**
 *
 * @author cloud
 */
public class FCodeBuilder {
    private final static String defaultUserConfigName="userCofig.json";
    public static Ioc ioc;
    private static  String cfgName="sysConfig.json";
    
    
    public static void readConfig() throws FileNotFoundException, IOException{
      
        File f=new File(cfgName);
        if(!f.exists()){//如果指定的文件不存在，生成默认配置
                saveDefaultConfig();
        }
        
        SysConfig syscfg=null;
        ioc= new NutIoc(new JsonLoader(cfgName));
        syscfg=ioc.get(SysConfig.class, "config");
   
        if( syscfg.getConfigControl()==null){
            syscfg.setConfigControl(new FileConfigRead(defaultUserConfigName));
        } 
        syscfg.getConfigControl().getConfig();
        
        ReadSource readtemplet=ioc.get(ReadSource.class, "readTempletFromFile");
        ReadSource readtempletData=ioc.get(ReadSource.class, "readTempleteDaTaFromFile");
        TDMatch tDMatch=new TDMatch(); 
        tDMatch.setReadTemplet(readtemplet);
        tDMatch.setReadTempletData(readtempletData);
        MatchNotify matchNotify=new MatchNotify();//matchNotify是 模数匹配器与模板引擎之间的连接器
        //此处将是以后性能变化修改点
        tDMatch.setMatchNotify(matchNotify);
        CodeEngine codeEngine=ioc.get(CodeEngine.class,"codeEngine");
        if(codeEngine==null){
            codeEngine=new FreeMakerEngine();
        }
        codeEngine.setMatchNotify(matchNotify);
        
        OutNotify outNotify=new OutNotify();
        codeEngine.setOutNotify(outNotify);
        OutControl outControl=ioc.get(OutControl.class, "outControl");
        if(outControl!=null){
            outControl.setOutNotify(outNotify);
        }
        
        //--------装配 end--------工作启动---------
        tDMatch.action();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        readConfig();
    }
    
    public static void saveDefaultConfig()throws FileNotFoundException, UnsupportedEncodingException, IOException{
         BufferedWriter bufWriter = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(cfgName,false), "UTF-8"
            )
        );  
               
        bufWriter.write("/* \r\n");     
        bufWriter.write(" */\r\n");
        bufWriter.write("{\r\n");
 
 
 

 
        bufWriter.write("  fileConfigRead:{ \r\n") ;
        bufWriter.write("    type:'fcodebuilder.cfg.FileConfigRead',\r\n") ;        
        bufWriter.write("    fields:{ \r\n") ;
        bufWriter.write("      file:'userCofig.json',  \r\n");
        bufWriter.write("    }  \r\n");  
        bufWriter.write("  },  \r\n");  
        
        bufWriter.write("  config:{ \r\n");
        bufWriter.write("    type:'fcodebuilder.cfg.Config$SysConfig',  \r\n");
        bufWriter.write("    fields:{  \r\n");     
        bufWriter.write("      configControl:{refer:'fileConfigRead'} \r\n");
        bufWriter.write("    } \r\n");
        bufWriter.write("  },  \r\n");       
        
        bufWriter.write("  readTempletFromFile:{ \r\n") ;
        bufWriter.write("    type:'fcodebuilder.in.ReadSourceFromFile',\r\n") ;        
        bufWriter.write("    fields:{ \r\n") ;
        bufWriter.write("    //0为读模板文件 \r\n ");
        bufWriter.write("      type:0  \r\n");
        bufWriter.write("    }  \r\n");  
        bufWriter.write("  },  \r\n");  
        
        bufWriter.write("  readTempleteDaTaFromFile:{ \r\n") ;
        bufWriter.write("    type:'fcodebuilder.in.ReadSourceFromFile',\r\n") ;        
        bufWriter.write("    fields:{ \r\n") ;
        bufWriter.write("    //1为读模板数据文件 \r\n");
        bufWriter.write("      type:1  \r\n");
        bufWriter.write("    }  \r\n");  
        bufWriter.write("  },  \r\n");  
        
        bufWriter.write("  outControl:{ \r\n") ;
        bufWriter.write("    type:'fcodebuilder.out.OutToFile'\r\n") ;    
        bufWriter.write("  },  \r\n");  
        
        bufWriter.write("  codeEngine:{ \r\n") ;
        bufWriter.write("    type:'fcodebuilder.engine.FreeMakerEngine',\r\n") ;   
        bufWriter.write("    fields:{  \r\n");     
        bufWriter.write("      outControl:{refer:'outControl'} \r\n");
        bufWriter.write("    } \r\n");
        bufWriter.write("  }  \r\n");  
  
        
        
        bufWriter.write("}\r\n");
        
        bufWriter.close();  
    }
}
