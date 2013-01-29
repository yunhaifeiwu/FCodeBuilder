/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.engine;

import fcodebuilder.match.MatchData;
import fcodebuilder.match.MatchNotify;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cloud
 */
public class FreeMakerEngine extends CodeEngine{

    public FreeMakerEngine(){}
    
    public FreeMakerEngine(MatchNotify matchNotify) {
        super(matchNotify);
    }
   
    @Override
    protected void action(MatchData matchData) {
        Configuration cfg = new Configuration();  
        cfg.setEncoding(Locale.getDefault(),"UTF-8");
        try {
            // 指定模板所在的基本目录   
            cfg.setDirectoryForTemplateLoading(  new File("")   );
        } catch (IOException ex) {
            Logger.getLogger(FreeMakerEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        Template template = null;
        try {
            template = cfg.getTemplate(matchData.getTemplate()); //加载模板文件
        } catch (IOException ex) {
            Logger.getLogger(FreeMakerEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

        File htmlFile = new File(matchData.getOutPath());
        File path= new File(htmlFile.getParent());
        if(!path.exists()){
            path.mkdirs();
        }
        Writer out = null;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(htmlFile), "UTF-8")); //输出文件
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FreeMakerEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FreeMakerEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            template.process(matchData.getTemplateData(), out);
        } catch (TemplateException | IOException ex) {
            Logger.getLogger(FreeMakerEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println();
    }
    
}
