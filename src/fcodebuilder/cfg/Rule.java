/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.cfg;

import java.util.LinkedHashMap;

/**
 *
 * @author cloud
 */
public class Rule {
    
    public static class Data{
        public String template;
        public String templateData;
        /**
         * 禁止对模板与模板数据的匹配。优先级别高于允许。
         */
        public  boolean disableIn ;
        public String out;
        /**
         * 禁止对产生的源码文件输出到out。优先级别高于允许。
         */
        public  boolean disableOut ;
  
        public Data(){}
        public Data(String template, String templateData, boolean disableIn,
                String out, boolean disableOut) 
        {
            this.template = template;
            this.templateData = templateData;
            this.disableIn = disableIn;
            this.out = out;
            this.disableOut = disableOut;
        }
        
    }
 
    private LinkedHashMap<String,Data>  dataRule=new LinkedHashMap<>();
    private LinkedHashMap<String,String>  templateIndex=new LinkedHashMap<>();

   /**
    * 按模板数据 组件的 所有规则存放
    */
    public LinkedHashMap<String, Data> getDataRule() {
        return dataRule;
    }

    /**
    * 按模板数据 组件的 所有规则存放
    */
    public void setDataRule(LinkedHashMap<String, Data> dataRule) {
        this.dataRule = dataRule;
    }

    /**
    * key为模板，value为dtaRule中的key. 目的：通过模板，快速查找到模板数据
    */
    public LinkedHashMap<String, String> getTemplateIndex() {
        return templateIndex;
    }

    /**
    * key为模板，value为dtaRule中的key目的：通过模板，快速查找到模板数据
    */
    public void setTemplateIndex(LinkedHashMap<String, String> templateIndex) {
        this.templateIndex = templateIndex;
    }

   
 
    
    public void putRule(String template, String templateData, 
             boolean disableIn,  String out,boolean disableOut )
    {
        
        Data data=new Data(template, templateData, disableIn, out,disableOut);
        dataRule.put(templateData, data);
        templateIndex.put(template,templateData );
    }
    
    
    public Data getDataFromTemplate(String template){
        String key=templateIndex.get(template);
        return dataRule.get(key);
    }
    
    public Data getData(String templateData){       
        return dataRule.get(templateData);
    }
    
    public static Data getDataInstance ( String template, String templateData, 
             boolean disableIn,  String out,boolean disableOut  )
    {
         
          Data data=new Data(template, templateData, disableIn, out,disableOut);
         
         return data;
    }
    
}
