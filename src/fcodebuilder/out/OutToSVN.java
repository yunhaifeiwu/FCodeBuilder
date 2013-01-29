/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.out;

import fcodebuilder.cfg.Config;
import fcodebuilder.svn.SvnUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author cloud
 */
public class OutToSVN extends OutControl {
    private String adress,username,paasword;
    private SvnUtil svnUtil;
    private int state=-1;//标识是否第一次执行action函数
    
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
    
    public OutToSVN(){}
    public OutToSVN(String adress, String username, String paasword)
    {
        this.adress = adress;
        this.username = username;
        this.paasword = paasword;
        this.svnUtil=new SvnUtil(adress,username,paasword);
         
    }
    
    
    
    @Override
    protected void action(String outPath) {
        if(svnUtil==null){
            Logger.getLogger(OutToSVN.class.getName()).
                log(Level.INFO, "OutToSVN.svnUtil为null:"
            );
        }
        if(state==-1){
            state=0;
            svnUtil.setBaseDirStr(Config.getUseConfig().getOutAdress());
        }
        try { 
            svnUtil.doCommit(outPath);
        } catch (SVNException ex) {
            Logger.getLogger(OutToSVN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
