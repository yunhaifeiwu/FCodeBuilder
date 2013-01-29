/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.in;

import fcodebuilder.cfg.Config;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cloud
 */
public class ReadSourceFromFile extends ReadSource {

    @Override
    public List<FileInfo> getFileInfosImpl() {//需增加路径忽略代码        
        List<FileInfo> rlist=new LinkedList();
        String base;
        if (type==ReadSource.TYPE_TEMPLET){
            base=Config.getUseConfig().getInTemplateAdress();
        } else{
            base=Config.getUseConfig().getInTemplateDataAdress();
        }
        
        File dir=new File(base);
        if(! dir.exists()){
            dir.mkdirs();
        }
        LinkedList<File>  list=new LinkedList<>();
        
        File files[]=dir.listFiles();
        for (File f:files){
            if(f.isDirectory()) {
                list.add(f);
            } else{
                rlist.add(new FileInfo(f.getPath().replaceAll("\\\\", "/")));
            }
        }
        File temp;
        while (!list.isEmpty()){
            temp=list.removeFirst();
            for(File f:temp.listFiles()){                
                if(f.isDirectory()) {
                    list.add(f);
                } else{
                    rlist.add(new FileInfo(f.getPath().replaceAll("\\\\", "/")));
                }
            }
        }
        return rlist;
    }
    
}
