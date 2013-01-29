/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.cfg;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cloud
 */
public class Config {
    //<editor-fold  defaultstate="collapsed" desc="Class ">
    //<editor-fold desc="UseConfig">
    public static class UseConfig {
      
        
        private String inCachPath="data/cache/in";
        private String outeCachPath="data/cache/out";   
        
        private String inTemplateType="file";
        private String inTemplateAdress="data/template";
        private String ignoreInTemplatePath=null;
        
        private String inTemplateDataType="file"; 
        private String inTemplateDataAdress="data/templateData";        
        private String ignoreInTemplateDataPath=null;        
 
        private String outType="file";
        private String outAdress="data/out";
        private String ignoreOutPath=null;        
       
        private Rule rules=new Rule();
        private Map extendMap;
        
        //<editor-fold defaultstate="collapsed" desc="getter and setter">

        public String getInTemplateDataAdress() {
            return inTemplateDataAdress;
        }

        public void setInTemplateDataAdress(String inTemplateDataAdress) {
            this.inTemplateDataAdress = inTemplateDataAdress;
        }
      
         

        public String getIgnoreInTemplateDataPath() {
            return ignoreInTemplateDataPath;
        }

        public void setIgnoreInTemplateDataPath(String ignoreInTemplateDataPath) {
            this.ignoreInTemplateDataPath = ignoreInTemplateDataPath;
        }

        public String getInTemplateAdress() {
            return inTemplateAdress;
        }

        public void setInTemplateAdress(String inTemplateAdress) {
            this.inTemplateAdress = inTemplateAdress;
        }

        public String getIgnoreInTemplatePath() {
            return ignoreInTemplatePath;
        }

        public void setIgnoreInTemplatePath(String ignoreInTemplatePath) {
            this.ignoreInTemplatePath = ignoreInTemplatePath;
        }

        public String getOutAdress() {
            return outAdress;
        }

        public void setOutAdress(String outAdress) {
            this.outAdress = outAdress;
        }

        public String getIgnoreOutPath() {
            return ignoreOutPath;
        }

        public void setIgnoreOutPath(String ignoreOutPath) {
            this.ignoreOutPath = ignoreOutPath;
        }

        public String getInCachPath() {
            return inCachPath;
        }

        public void setInCachPath(String inCachPath) {
            this.inCachPath = inCachPath;
        }

        public String getOuteCachPath() {
            return outeCachPath;
        }

        public void setOuteCachPath(String outeCachPath) {
            this.outeCachPath = outeCachPath;
        }

        public String getInTemplateType() {
            return inTemplateType;
        }

        public void setInTemplateType(String inTemplateType) {
            this.inTemplateType = inTemplateType;
        }

        public String getInTemplateDataType() {
            return inTemplateDataType;
        }

        public void setInTemplateDataType(String inTemplateDataType) {
            this.inTemplateDataType = inTemplateDataType;
        }

        public String getOutType() {
            return outType;
        }

        public void setOutType(String outType) {
            this.outType = outType;
        }

        /**
         *  用户自定义规则
         */
        public Map getExtendMap() {
            return extendMap;
        }

        /**
         *  用户自定义规则
         */
        public void setExtendMap(Map extendMap) {
            this.extendMap = extendMap;
        }

        
        public Rule getRules() {
            return rules;
        }

        public void setRules(Rule rules) {
            this.rules = rules;
        }
        
        //</editor-fold >
        
        
    }
    //</editor-fold>
    //<editor-fold defaultstatus="collapsed" desc=" SysConfig">
    public static class SysConfig {
        public final static String USER_CONFIG_TYPE_FILE="file";
    
       
        private ConfigControl configControl;
        private Map extendMap;
        
        
        //<editor-fold  defaultstate="collapsed" desc=" getter and setter ">
        

        public ConfigControl getConfigControl() {
            return configControl;
        }

        public void setConfigControl(ConfigControl configControl) {
            this.configControl = configControl;
        }

        
        
        
        /**
         *  用户自定义规则
         */
        public Map getExtendMap() {
            return extendMap;
        }

        /**
         *  用户自定义规则
         */
        public void setExtendMap(Map extendMap) {
            this.extendMap = extendMap;
        }
        //</editor-fold>
    }
    //</editor-fold>
   
    private static UseConfig useConfig=new UseConfig();
    private static SysConfig sysConfig=new SysConfig();

    public static UseConfig getUseConfig() {
        return useConfig;
    }

    public static void setUseConfig(UseConfig useConfig) {
        Config.useConfig = useConfig;
    }

    public static SysConfig getSysConfig() {
        return sysConfig;
    }

    public static void setSysConfig(SysConfig sysConfig) {
        Config.sysConfig = sysConfig;
    }
    
    //<editor-fold defaultstate="collapsed" desc="单例代码">
    private final static Config config=new Config();
    private Config (){};
    public static Config getConfig(){
       return config;
    }
    private static Class getClass(String classname) throws ClassNotFoundException
    {    
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();    
        if(classLoader == null) {
            classLoader = Config.class.getClassLoader();
        }    
        return (classLoader.loadClass(classname));    
   }    
    private Object readResolve() {    
            return config;    
   }   
    //</editor-fold>
    
    public static void userConfigFromMap(Map map){
      
        
        String str=(String) map.get("inCachePath");
        Config.getUseConfig().setInCachPath(str);      
        str=(String) map.get("outCachePath");
        Config.getUseConfig().setInCachPath(str);    

        str=(String) map.get("inTemplateType");
        Config.getUseConfig().setInTemplateType(str);      
        str=(String) map.get("inTemplateAdress");
        Config.getUseConfig().setInTemplateAdress(str);
        str=(String) map.get("ignoreInTemplatePath");
        Config.getUseConfig().setIgnoreInTemplatePath(str);

        str=(String) map.get("inTemplateDataType");        
        Config.getUseConfig().setInTemplateDataType(str);
        str=(String) map.get("inTemplateDateAdress");
        Config.getUseConfig().setInTemplateDataAdress(str);
        str=(String) map.get("ignoreInTemplateDataPath");
        Config.getUseConfig().setIgnoreInTemplateDataPath(str);

        str=(String) map.get("outType");        
        Config.getUseConfig().setOutType(str);
        str=(String) map.get("outAdress");
        Config.getUseConfig().setOutAdress(str);
        str=(String) map.get("ignoreOutPath");
        Config.getUseConfig().setIgnoreOutPath(str);
        
        List<Map> rulem=(List) map.get("rules");
        Rule rule=Config.getUseConfig().getRules();
        for(Map mp:rulem){
            
            boolean ig= mp.get("igore")!=null &&
                    mp.get("igore").equals("true")?true:false;
            boolean dis=mp.get("disableOut")!=null && 
                    mp.get("disableOut").equals("true")?true:false; 
            String t=mp.get("template").toString();
            t=wildcard("^",t) ;
            String td=mp.get("templateData").toString();
            t=wildcard("^",t) ;
            rule.putRule(t, td,
                    ig, mp.get("out").toString(), dis);
        }
        
        
        Map emap=(Map) map.get("extendMap");
        Config.getUseConfig().setExtendMap(emap);
        
        
    }
    
    public static void sysConfigFromMap(Map map){
   
       
        Map emap=(Map) map.get("extendMap");
        Config.getSysConfig().setExtendMap(emap);
        
        
    }
     /**
     * 把通配符字符串转换成 正则字符串，便于分析处理
     * @param pre 前缀
     * @param s 含 通配符的字符串
     *  
     */
    public static String wildcard(String pre,String s){
        StringBuilder sb= (pre==null|| pre.equals("") )?   new StringBuilder(s)
                :new StringBuilder(pre).append(s);
        StringBuilder d=new StringBuilder();
        char xc='*';
        char qc='?';
        char tc='\\';
        int state=0;//状态机
        for(int i=0;i<sb.length();i++){
            if( state==0 && (sb.charAt(i))==xc ){ 
                d.append("[^2|2]*");
            }else if(state==0 && sb.charAt(i)==qc){
                d.append("[^2|2]{1}");
            } else if(sb.charAt(i)==tc){//为转义移时，不输出，状态转为1
                state=1;
            } else {//如果上次是转义符时，则原符输出；如果不是，则仅当不是通配符才原符输出
                d.append(sb.charAt(i));
                state=0;
            }
        }
        
        return d.toString();
        
    }
}
