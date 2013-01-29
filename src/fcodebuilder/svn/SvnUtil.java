/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.svn;


import fcodebuilder.util.DeleteDirectory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class SvnUtil {
    private String address,username,password;
    private String baseDirStr;//存放本地文件的初始目录。第一次check前设置
    private SVNClientManager svnManger;
    private SVNURL repositoryURL;
    private SVNRepository repository ;
 
    
    //<editor-fold  defaultstate="collapsed" desc="getter and setter ">
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * 存放本地文件的初始目录。第一次check前设置
     */
    public String getBaseDirStr() {
        return baseDirStr;
    }
    
    /*
     * 存放本地文件的初始目录。第一次check前设置
     */
    public void setBaseDirStr(String baseDirStr) {
        this.baseDirStr = baseDirStr;
    }

  
    
    
    //</editor-fold>
    
    public SvnUtil(){ }
    
    /**
     * 
     * @param address  svn服务器上的Repository的网址
     * @param username svn服务器上的Repository的账户
     * @param password svn服务器上的Repository的密码
     */
    public SvnUtil(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
         
        createManger(address,username,password);
    }
    
    
    public SVNClientManager createManger (String address,String username,
            String password) 
    {
        //初始化支持svn://协议的库。 必须先执行此操作。
        SVNRepositoryFactoryImpl.setup();
        //相关变量赋值
        repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(address);
        } catch (SVNException e) {
            Logger.getLogger(SvnUtil.class.getName()).
                    log(Level.SEVERE, address, e);
        }
       
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //实例化客户端管理类
        svnManger = SVNClientManager.newInstance(
            (DefaultSVNOptions) options, username, password
        );
        try { 
            repository = svnManger.createRepository(repositoryURL, true);
        } catch (SVNException ex) {
            Logger.getLogger(SvnUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(SvnUtil.class.getName()).
                    log(Level.INFO, "创建svnManger成功");
        return svnManger;
    }
    
    public SVNClientManager createManger (){        
        return createManger(address,username,password);
    }
    
    /**
     * 让指定目录成为SVN的初始化工作目录，被把服务器上指定的资料COPY到这里。<br>
     * 注意：1 在为本地基地址执行checkout，应设置baseDirStr<br>
     *   2 本功能是通过doCheckOut 让 服务器与客户端的目录要分别对应
     * @param checkOutDir  客户端中相对目录（以baseDirStr为开始的目录）
     * @return 返回-1表示失败，反之返回下载的版本号
     * 
     */
    public long doCheckOut(String checkOutDir){
        if(svnManger==null){ createManger();}
        DeleteDirectory.deleteDir(checkOutDir+"/.svn");
        //要把版本库的内容check out到的目录
        File wcDir = new File(checkOutDir);
        SVNURL url=repositoryURL;
        try {
            String[] strs=checkOutDir.split("^"+baseDirStr);
            if(strs!=null && strs.length>0){
                Logger.getLogger( SvnUtil.class.getName()).log(
                    Level.INFO, "baseDirStr:"+baseDirStr
                );
                url=url.appendPath(strs[1], false);
                
            }
        }  catch (SVNException ex ) {
            
            Logger.getLogger( SvnUtil.class.getName()).log(
                Level.SEVERE,null, ex
            );
        }
   
        //通过客户端管理类获得updateClient类的实例。
        SVNUpdateClient updateClient = svnManger.getUpdateClient();
        updateClient.setIgnoreExternals(false);        
        try {
            //执行check out 操作，返回工作副本的版本号。 
            //这里的url 与wcDir是相对应的。前者代表服务上的，后者代表客户端的
            long workingVersion= updateClient.doCheckout(
                url, wcDir, SVNRevision.HEAD,
                SVNRevision.HEAD,  SVNDepth.INFINITY,true
            );            
            return workingVersion;
        } catch (SVNException ex) {
            Logger.getLogger(SvnUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }
    
    
    /**
     * commitFileName 所指文件，必须在执行过doCheckOut的文件夹中。把工作目录中<br>
     * 指定的文件上传到服务器中
     * @param commitFileName 本地客户端的相对目录。
     */
    public void doCommit(String commitFileName) throws SVNException{
        if(svnManger==null){ createManger();}
        //要提交的文件
        File commitFile=new File(commitFileName);
        
        String parentstr=commitFileName.split("/[^/]*$")[0];
        String relativ="";
        //baseDirStr是本地其目录。这里是为了得到去掉基目录的相对目录
        String[] strs= parentstr.split("^"+baseDirStr+"/{0,1}");
        if(strs!=null&& strs.length>0){
            relativ=strs[1];
        }
        File parentFile=new File(parentstr);
        //获取此文件的状态（是文件做了修改还是新添加的文件？）
        
        SVNStatus parentstatus=null;
        try {
            parentstatus=svnManger.getStatusClient().doStatus(parentFile, true);
        } catch (Exception e){
            doCheckOut(parentstr);//详见    doCcheckOut小结
        }
        
        if( !relativ.equals("") && (parentstatus==null ||                
            ( parentstatus.getRemoteRevision()==SVNRevision.UNDEFINED &&
              parentstatus.getContentsStatus()!=SVNStatusType.STATUS_NORMAL ) )
        ){
            SVNURL url=repositoryURL;
            url=url.appendPath(relativ, false);            
            svnManger.getCommitClient().doMkDir(
                new SVNURL[]{url}, "add"+url.getPath()
            ); 
        }
        
        //如果此文件是新增加的则先把此文件添加到版本库，然后提交。
        SVNStatus status=null;
        try {
            status=svnManger.getStatusClient().doStatus(commitFile, true);
        } catch (Exception e){
            doCheckOut(parentstr);     
        }
        if(status==null || 
            status.getRemoteRevision()== SVNRevision.UNDEFINED 
        ){ 
            //预增加文件，如果父目录不存，也预增加父目录
            svnManger.getWCClient().doAdd(
                commitFile, true,false, false, SVNDepth.INFINITY,false,true
            );

            //提交此文件
            svnManger.getCommitClient().doCommit(
                new File[] { commitFile },
                true, "",null,null,false, true, SVNDepth.INFINITY
            );
            Logger.getLogger(SvnUtil.class.getName()).
                log(Level.INFO, "addCommit成功:"+commitFileName
            ); 
        } else{ //如果此文件不是新增加的，直接提交。
            svnManger.getCommitClient().doCommit(
                    new File[] { commitFile },
                    true, "",null,null,true, true,  SVNDepth.INFINITY
            );
            Logger.getLogger(SvnUtil.class.getName()).
                log(Level.INFO, "editCommit成功:"+commitFileName
            );
        }
    }
    
    /**
     * 把服务器上的updateFileName 文件，放到本地文件夹中。要求文件夹，<br>
     * 必须执行过doCheckOut。 
     * @return 返回-1表示失败，反之返回下载的版本号
     */
    public long doUpdate(String updateFileName) throws SVNException{
        if(svnManger==null){ createManger();}
        
        //要更新的文件
        File updateFile=new File(updateFileName);
        //获得updateClient的实例
        SVNUpdateClient updateClient = svnManger.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        //执行更新操作
        long versionNum= updateClient.doUpdate(
                updateFile, SVNRevision.HEAD, SVNDepth.INFINITY,false,false
        );
        System.out.println("工作副本更新后的版本："+versionNum);
        if (versionNum==-1){
            Logger.getLogger(SvnUtil.class.getName()).
                log(Level.INFO, "下载文件处理:"+updateFileName+
                    "。版本号："+versionNum+"(-1下载失败)"
            );
        }
            
       
        return versionNum;
    }
  
}
