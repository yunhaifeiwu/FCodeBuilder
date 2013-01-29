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

/*此类执行的操作是把版本库中的内容check out到本地目录中*/
public class CheckOut {
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
		
		//要把版本库的内容check out到的目录
		File wcDir = new File("E:/fyh/git/FCodeBuilder/data/out");
		
		//通过客户端管理类获得updateClient类的实例。
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		/*
		 * sets externals not to be ignored during the checkout
		 */
		updateClient.setIgnoreExternals(false);
		
		//执行check out 操作，返回工作副本的版本号。
		long workingVersion= updateClient
				.doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
		
		System.out.println("把版本："+workingVersion+" check out 到目录："+wcDir+"中。");

	}

			
}

