/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.in;

import fcodebuilder.cfg.Config;
import fcodebuilder.svn.SvnUtil;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author cloud
 */
public class ReadSourceFromSvn  extends ReadSource {
    private String adress,username,paasword;
    private SvnUtil svnUtil; 
    
    private int state=-1;//标识是否第一次执行getFileInfosImpl函数
    
    //<editor-fold  defaultstate="collapsed" desc="getter and setter ">
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaasword() {
        return paasword;
    }

    public void setPaasword(String paasword) {
        this.paasword = paasword;
    }

    public SvnUtil getSvnUtil() {
        return svnUtil;
    }

    public void setSvnUtil(SvnUtil svnUtil) {
        this.svnUtil = svnUtil;
    }
     //</editor-fold>
    
    public ReadSourceFromSvn(){}
    public ReadSourceFromSvn(String adress, String username, String paasword)
    {
        this.adress = adress;
        this.username = username;
        this.paasword = paasword;
        this.svnUtil=new SvnUtil(adress,username,paasword);         
    }
    
    @Override
    public List<FileInfo> getFileInfosImpl() {
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
        
        //从svn下载模板数据
        if(state==-1){
            state=0;
            svnUtil.setBaseDirStr(base);
        }
        svnUtil.doCheckOut(base);
        try {
            svnUtil.doUpdate(base);
        } catch (SVNException ex) {
            Logger.getLogger(ReadSourceFromSvn.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
         
        
        //-------------读 下载到本地的文件---------开始-------
        
        LinkedList<File>  list=new LinkedList<>();
        
        File files[]=dir.listFiles();
        for (File f:files){
            if(f.isDirectory() && !f.getName().equals(".svn")) {
                list.add(f);
            } else if(f.isFile()&& !f.getName().equals(".svn") ){
                rlist.add(new FileInfo(f.getPath().replaceAll("\\\\", "/")));
            }
        }
        File temp;
        while (!list.isEmpty()){
            temp=list.removeFirst();
            for(File f:temp.listFiles()){                
                if(f.isDirectory() && !f.getName().equals(".svn") ) {
                    list.add(f);
                } else  if(f.isFile()&&!f.getName().equals(".svn") ){
                    rlist.add(new FileInfo(f.getPath().replaceAll("\\\\", "/")));
                }
            }
        }
        //-------------读 下载到本地的文件---------结束-------
        return rlist;
    }
    
}
