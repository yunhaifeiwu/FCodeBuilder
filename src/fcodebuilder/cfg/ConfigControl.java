/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.cfg;

/**
 *
 * @author cloud
 */
public abstract class ConfigControl {
    private String file;
    public ConfigControl(){}

    public ConfigControl(String file) {
        this.file = file;
    }

    
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    
    
    public Config getConfig(String file){
        Config cfg=getConfigImpl(file);
        return cfg;
        
    }
        
    public Config getConfig(){
        Config cfg=getConfigImpl(file);
        return cfg;        
    }
    
    public abstract Config getConfigImpl(String file);
    
    
    
        
}
