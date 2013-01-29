/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.match;

import fcodebuilder.cfg.Config;
import fcodebuilder.cfg.Rule;
import fcodebuilder.in.FileInfo;
import fcodebuilder.in.ReadSource;
import fcodebuilder.out.OutNotify;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.nutz.json.Json;

/**
 *
 * @author cloud
 */
public class TDMatch {
    /**
     * 为与json相映射的Map的访问而作。
     * 表示key[pos];即表示关键字为key的元素的第pos个值。
     */
    public static class MKey{
        /**
         * 记录map中key值
         */
        public String key="";
        /**
         * 记录数组中的位置
         */
        public int pos=-1;
        
        
    }
    private  transient  String outb,outbi,t,ti,td,tdi;
    private  transient Rule rule;
    
    
    private ReadSource readTemplet;
    private ReadSource  readTempletData;
    private MatchNotify matchNotify;
    private Rule regRule;
   

    public ReadSource getReadTemplet() {
        return readTemplet;
    }

    public void setReadTemplet(ReadSource readTemplet) {
        this.readTemplet = readTemplet;
    }

    public ReadSource getReadTempletData() {
        return readTempletData;
    }

    public void setReadTempletData(ReadSource readTempletData) {
        this.readTempletData = readTempletData;
    }

    public MatchNotify getMatchNotify() {
        return matchNotify;
    }

    public void setMatchNotify(MatchNotify matchNotify) {
        this.matchNotify = matchNotify;
    }

    
    
    
    /**
     * 进行数据匹配操作。
     */
    public void action(){
        if(readTemplet==null) {
            Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, 
                    "注意：readTemplet为null!系统退出！"
            );
            return;
        }
        
        if(readTempletData==null) {
            Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, 
                    "注意：readTempletData为null!系统退出！"
            );
            return;
        }
        
        
      
        List<FileInfo> tlist=readTemplet.getFileInfos();
        if(tlist==null || tlist.size()<=0) {
            Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, 
                    "注意：rreadTemplet.getFileInfos为null或无记录!系统退出！"
            );
            return;
        }
        List<FileInfo> tdlist=readTempletData.getFileInfos();
        if(tlist==null || tlist.size()<=0) {
            Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, 
                    "注意：rreadTemplet.getFileInfos为null或无记录!系统退出！"
            );
            return;
        }
      
        
        
        outb=Config.getUseConfig().getOutAdress();
        outbi=Config.getUseConfig().getIgnoreOutPath();
        t=Config.getUseConfig().getInTemplateAdress();
        ti=Config.getUseConfig().getIgnoreInTemplatePath();
        td=Config.getUseConfig().getInTemplateDataAdress();
        tdi=Config.getUseConfig().getIgnoreInTemplateDataPath();
        rule=Config.getUseConfig().getRules();
        actionDefault(tlist,tdlist);
        if(rule==null || rule.getDataRule()==null ||
                        rule.getDataRule().size()<=0)
        {
            actionDefault(tlist,tdlist);//模板与数据 的路径（含主名）对应相等则匹配
            return;
        }
        
   
        
        Pattern p; Matcher m ;
        for(FileInfo fd:tdlist){
            if(tdi!=null){
                p =   Pattern.compile(tdi);
                m = p.matcher(fd.getName());
                if(m.find()) {continue;}
            }

            
            for(FileInfo f:tlist){
                if(ti!=null){
                    p =   Pattern.compile(ti);
                    m = p.matcher(f.getName());
                    if(m.find()) {continue;}
                }
                
                for (Map.Entry<String,Rule.Data> en:rule.getDataRule().entrySet()){
                      actionUnit(f,fd,en.getValue());
                }
                
            }
            
         
        }
        System.err.println();
        
        
    }
    /**
     * //模板与模板数据的路径与文件主名对应相同则匹配成功
     */
    private void actionDefault(List<FileInfo> tlist,List<FileInfo> tdlist){
        Pattern p; Matcher m ;
        for(FileInfo fd:tdlist){
            if(tdi!=null){
                p =   Pattern.compile(tdi);
                m = p.matcher(fd.getName());
                if(m.find()) {continue;}
            }

            
            for(FileInfo f:tlist){
                if(ti!=null){
                    p =   Pattern.compile(ti);
                    m = p.matcher(f.getName());
                    if(m.find()) {continue;}
                }
                
                 
                String tempstr=f.getName();
                String[] aa=tempstr.split(t);
                String outPath=outb;
                if(aa.length>1){
                    aa=aa[1].split("\\.[^2|2]*$");
                    outPath=outPath+aa[0];
                    aa=aa[0].split("(\\.[^2|2]*)*$");
                }else {
                    aa=aa[1].split("\\.[^2|2]*$");
                    outPath=outPath+aa[0];
                    aa=aa[0].split("(\\.[^2|2]*)*$");
                }
               
                String tempDatastr=fd.getName();
                String[] aa1=tempDatastr.split(td);
                if(aa1.length>1){
                    aa1=aa1[1].split("(\\.[^2|2]*)*$");
                }else {
                    aa1=aa1[0].split("(\\.[^2|2]*)*$");
                }
                if(aa[0].equals(aa1[0])){
                    MatchData matchData=new MatchData();
                    matchData.setOutPath(outPath);
                    matchData.setTemplateData(productTemplateData(fd.getName()));
                    matchData.setTemplate(f.getName());
                    this.getMatchNotify().postMatchData(matchData);
                }
                System.err.println();
            }
              
         
        }
    }
    
    /**
     * 把模板文件中最后一个 “.“及其后的字母去除掉，并加上输出基目录。
     * @param filename  经过${}转换后的模板文件名
     * @return  返回最终输出路径。
     */
    private String fileExtensionsAndOutChange(String filename){
         
        
        String[] aa=filename.split(t);
        String outPath=outb;
        if(aa.length>1){
            aa=aa[1].split("\\.[^\\.]*$");
            outPath=outPath+aa[0];
        }else {
            aa=aa[1].split("\\.[^\\.]*$");
            outPath=outPath+aa[0];
        }
 
 
        return outPath;
    }
    
    private void actionUnit(FileInfo temp,FileInfo tempdata,Rule.Data ruled){
        
        Pattern p=Pattern.compile(ruled.template);
        Matcher m=p.matcher(temp.getName());
        boolean tbl=m.find();
        p=Pattern.compile(ruled.templateData);
        m=p.matcher(tempdata.getName());
        boolean tdbl=m.find();
        if(tbl && tdbl && !ruled.disableIn){//构造输出
            
            Map<String, Object> map=productTemplateData(tempdata.getName());
            Object obj=map.get("_each");
            if(obj==null || !List.class.isAssignableFrom(obj.getClass())){return;}
            List<Map<String, Object> > list=(List<Map<String, Object> >) obj;
            List<Map<String, Object> > lst=new LinkedList<>();
            Map<String, Object> map1=new LinkedHashMap<>();
            map1.put("_path", map.get("_path"));
            map1.put("_public", map.get("public"));
            for(Map<String, Object> mp:list){
                
                map1.clear(); 
                lst.clear();
                lst.add(mp);
                map1.put("_each", lst);
                String outPath=getPathFromWild_card( temp.getName() ,map1);
                outPath=fileExtensionsAndOutChange(outPath);
                MatchData matchData=new MatchData();
                matchData.setOutPath(outPath);

                mp.put("_path", map.get("_path"));
                mp.put("_public", map.get("public"));
                matchData.setTemplateData(mp);
                matchData.setTemplate(temp.getName());
                this.getMatchNotify().postMatchData(matchData);
            }
            
                
        } 
    }
    
    /**
     * 把 含${}形式的通配符的路径，按给定的数据源，转换成路径.
     * 例${name},${name[0]},${name[0].address.state}
     * @param wild_cardPath  含${}形式的通配符的路径
     * @param templetData  模板数据源。 json结构的map表达。
     * @return 返回转换后的路径
     */
    private String  getPathFromWild_card(String wild_cardPath,Map  templetData){
        StringBuilder sb=new StringBuilder(wild_cardPath);
        StringBuilder rt=new StringBuilder();
        StringBuilder key=new StringBuilder();
        StringBuilder pos=new StringBuilder();
        LinkedList<MKey> link=new LinkedList<>();
        Boolean  state2BL=false;
        char dl='$';
        char lb='{';
        char rb='}';
        char p='.';
        char lmb='[';
        char rmb=']';
        int state=0;
        for (int i=0;i<sb.length();i++){      
            
            char cs=sb.charAt(i); 
            
            if (state==0 && cs==dl){// "$"
                 state=1;               
             } else if(state==0 && cs!=dl){// 非 "$"
                 state=0;
                 rt.append(cs);
             } else if (state==1 && cs==lb ){// "{"
                 state=2;
                 key=key.delete(0, key.length());                
                 state2BL=true;
             } else if (state==1 && cs!=lb ){// 非"{" 
                 state=0;
                 rt.append("$");
                 rt.append(cs);
             }else if (state==2 &&  cs!=p && cs!=lmb && cs!=rmb && 
                 cs!=rb        
             ){// 非"." 、非“【”、非“】”、非“｝” 时记录key
                 state=2;
                 key.append(cs);  
                 state2BL=false;
             }else if (state==2 && ( state2BL &&
                    ( cs==p || cs==lmb || cs==rmb ||  cs==rb ) 
                   || !state2BL &&( cs==lb || cs== rmb ) )
             ){//  
                 state=0;
                 rt.append(dl) ;
                 rt.append(lb);                
                 rt.append(cs);
             } else if ((state==2) && cs==rb && !state2BL ){// "}"  
                 state=0;
                 MKey mk=new MKey();
                 mk.key=key.toString();
                 link.add(mk);
                 rt.append(getValueFromLink_Map(link,templetData));
                 key=key.delete(0, key.length());   
             } else if (state==2 && cs==p ){// "."   存在链表中，状态不变
                 state=2;
                 MKey mk=new MKey();
                 mk.key=key.toString();
                 link.add(mk);
                 key=key.delete(0, key.length());   
             }  else if (state==2 && cs==lmb ){// "["    
                 pos.delete(0, pos.length());
                 state=3; 
             } else if (state==3 && cs!=rmb ){// 非"]"    
                 pos.append(cs);
             } else if (state==3 && cs==rmb ){// "]"    
                 state=4;
                 Pattern pattern = Pattern.compile("[0-9]*");  
                 if( pattern.matcher(pos).matches() ){
                     MKey mk=new MKey();
                     mk.key=key.toString();
                     mk.pos=Integer.valueOf(pos.toString());
                     link.add(mk);
                     pos.delete(0, pos.length());
                     key=key.delete(0, key.length());  
                 } else {
                     key.append(lmb);
                     key.append(pos);
                     key.append(rmb);                            
                 }
                  
             } else if (state==4 && cs==p ){// "."                     
                state=2;
                key=key.delete(0, key.length());  
             } else if (state==4 && cs==rb  ){// "}"   
                state=0;
                rt.append(getValueFromLink_Map(link,templetData));
             } else if (state==4 && cs!=p  &&  cs!=rb ){// 非"." 非 "}"  
                 state=0;
                 key=key.delete(0, key.length());  
             }
            
             
            
        }
        return rt.toString();        
    }
    
    /**
     *  在Map中按key取值。其中：链表存储的是key，链表中每一个节点，对应子key.<br>
     * 一个链表的从0节点到最后一个节点，分表对应map深度。 <br>
     *  寻找方法：开始先取_path,再取_public,最后在_each下寻找。
     * 
     * @param list  存储一系列key.链表中每一个节点，对应一个key.链表对应map的深度
     * @param templetData  模板数据源。 json结构的map表达。
     * @return  返回 list指定key的值
     */
    public String getValueFromLink_Map(LinkedList<MKey> list,
            Map<String, Object>  templetData)
    {
        int i=-1;
        Object obj=(Map<String, Object>) templetData.get("_path");
        Map<String, Object>  map = null;
        List<Map<String, Object>> listmap;
        if(obj !=null && Map.class.isAssignableFrom(obj.getClass())){
            map=(Map<String, Object>) obj;
            if(map.size()>0){
                i=-1;
                for (MKey key:list){
                   i++; 

                   obj=((Map)obj).get(key.key);
                   if(obj==null){break;}

                   if( (i<list.size()-1) &&  ( ( key.pos<0
                           && !Map.class.isAssignableFrom(obj.getClass())  ) )
                          ||  (key.pos>0  && !List.class.isAssignableFrom(obj.getClass() )  )   
                   ) {
                       break;
                   }  else if ( (i<list.size()-1) && ( key.pos>=0 )  && obj!=null&&
                           List.class.isAssignableFrom(obj.getClass())
                   ){
                       obj=((List)obj).get(key.pos);
                   } else if( (i==list.size()-1) && key.pos<0 && obj!=null &&
                       String.class.isAssignableFrom(obj.getClass()) 

                   ){ 
                       return ((String)obj);
                   }else if( (i==list.size()-1) && key.pos>=0&&
                       List.class.isAssignableFrom(obj.getClass()) 

                   ){ 
                       obj=((List)obj).get(key.pos);
                       if(obj!=null ){return obj.toString();}
                   }
                }//遍历
            }
        } 
        
        obj=(Map<String, Object>) templetData.get("_public");
        if(obj !=null && Map.class.isAssignableFrom(obj.getClass())){
            map=(Map<String, Object>) obj;
            if(map.size()>0){
                i=-1;
                for (MKey key:list){
                   i++; 

                   obj=((Map)obj).get(key.key);
                   if(obj==null){break;}

                   if( (i<list.size()-1) &&  ( ( key.pos<0
                           && !Map.class.isAssignableFrom(obj.getClass())  ) )
                          ||  (key.pos>0  && !List.class.isAssignableFrom(obj.getClass() )  )   
                   ) {
                       break;
                   }  else if ( (i<list.size()-1) && ( key.pos>=0 )  && obj!=null&&
                           List.class.isAssignableFrom(obj.getClass())
                   ){
                       obj=((List)obj).get(key.pos);
                   } else if( (i==list.size()-1) && key.pos<0 && obj!=null &&
                       String.class.isAssignableFrom(obj.getClass()) 

                   ){ 
                       return ((String)obj);
                   }else if( (i==list.size()-1) && key.pos>=0&&
                       List.class.isAssignableFrom(obj.getClass()) 

                   ){ 
                       obj=((List)obj).get(key.pos);
                       if(obj!=null ){return obj.toString();}
                   }
                }//遍历
            }
        }
       
        List<Map<String,Object>> listm=null;
        listm=(List<Map<String,Object>>) templetData.get("_each");
        for(Map<String,Object> map1:listm){
            obj=map1;
            if(obj !=null && Map.class.isAssignableFrom(obj.getClass())){
                map=(Map<String, Object>) obj;
                if(map.size()>0){
                    i=-1;
                    for (MKey key:list){
                       i++; 
                       
                       obj=((Map)obj).get(key.key);
                       if(obj==null){break;}

                       if( (i<list.size()-1) &&  ( ( key.pos<0
                               && !Map.class.isAssignableFrom(obj.getClass())  ) )
                              ||  (key.pos>0  && !List.class.isAssignableFrom(obj.getClass() )  )   
                       ) {
                           break;
                       }  else if ( (i<list.size()-1) && ( key.pos>=0 )  && obj!=null&&
                               List.class.isAssignableFrom(obj.getClass())
                       ){
                           obj=((List)obj).get(key.pos);
                       } else if( (i==list.size()-1) && key.pos<0 && obj!=null &&
                           String.class.isAssignableFrom(obj.getClass()) 

                       ){ 
                           return ((String)obj);
                       }else if( (i==list.size()-1) && key.pos>=0&&
                           List.class.isAssignableFrom(obj.getClass()) 

                       ){ 
                           obj=((List)obj).get(key.pos);
                           if(obj!=null ){return obj.toString();}
                       }
                    }//遍历
                }
            }
        }
        return null;
        
    }
    /**
     * 把数据模板所需数据，读到map中去。 
     * 注意：这里规范 枨式为：json. 且 第一层元素 为控制类元素。第一层中 _each，<br/>
     * 表示其下的每个子节点对应着匹配的结点。_path 下存放着 路径的名。_public <br/>
     * 表示公有变量     * 
     * @param dataFileName  数据配置文件名
     * @return 
     */
    private Map<String,Object> productTemplateData(String dataFileName){
        InputStreamReader is = null;
        try {
            try {
                is = new InputStreamReader(
                  new FileInputStream(dataFileName), "UTF-8" );
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        Map<String,Object>  map =null;
        try{ 
           map=(Map) Json.fromJson(is);
        } catch(Exception e){
           Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, "出错文件："+dataFileName, e);
        }
        try {
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(TDMatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                 
        return map;
        
    }
    
}
