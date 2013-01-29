/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.svn;

import java.io.File;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/*此类用来把版本库中文件的某个版本更新到工作副本中*/
public class DoUpdate {
    //声明SVN客户端管理类
    private static SVNClientManager ourClientManager;

    public static void main(String[] args) throws Exception {
        //初始化支持svn://协议的库。 必须先执行此操作。
        SVNRepositoryFactoryImpl.setup();
        //相关变量赋值
        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded("https://mis/svn/out/");
        } catch (SVNException e) {
                //
        }
        String name = "admin";
        String password = "123";

        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //实例化客户端管理类
        ourClientManager = SVNClientManager.newInstance(
                        (DefaultSVNOptions) options, name, password);
        //要更新的文件
        File updateFile=new File("E:/fyh/git/FCodeBuilder/data/out/bb.txt");
        //获得updateClient的实例
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        //执行更新操作
        long versionNum= updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY,false,false);
        System.out.println("工作副本更新后的版本："+versionNum);

    }

	
}

