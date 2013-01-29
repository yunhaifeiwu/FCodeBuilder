/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.match;

import java.util.Map;

/**
 *
 * @author cloud
 */
public class MatchData {
    private String template;
    private Map<String,Object> templateData;
    private String outPath;
    private String actionState;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }

     

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }
    
    
}
