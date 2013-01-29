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
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
/*此类的操作是把工作副本的某个文件提交到版本库中*/
public class DoCommit {
	//	声明SVN客户端管理类
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
		//要提交的文件
		File commitFile=new File("E:/fyh/git/FCodeBuilder/data/out/out/bb.txt");
//                File commitFile=new File("e:/aa.txt");
		//获取此文件的状态（是文件做了修改还是新添加的文件？）
		SVNStatus status=ourClientManager.getStatusClient().doStatus(commitFile, true);
		//如果此文件是新增加的则先把此文件添加到版本库，然后提交。
		if(status==null || status.getContentsStatus()==SVNStatusType.STATUS_UNVERSIONED){
			//把此文件增加到版本库中
			ourClientManager.getWCClient().doAdd(commitFile, false, false, false, SVNDepth.INFINITY,false,false);
			//提交此文件
			ourClientManager.getCommitClient().doCommit(
					new File[] { commitFile }, true, "",null,null,true, false, SVNDepth.INFINITY);
			System.out.println("add");
		}
		//如果此文件不是新增加的，直接提交。
		else{
//			ourClientManager.getCommitClient().doCommit(
//					new File[] { commitFile }, true, "", false, true);
			ourClientManager.getCommitClient().doCommit(
					new File[] { commitFile }, true, "",null,null,true, false, SVNDepth.INFINITY);
			System.out.println("commit");
		}
		System.out.println(status.getContentsStatus());
				
	}

	
	
}
