/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.out;

import fcodebuilder.match.MatchData;
import fcodebuilder.match.MatchNotify;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cloud
 */
public abstract class OutControl implements PropertyChangeListener {
    private OutNotify outNotify;

    public OutNotify getOutNotify() {
        return outNotify;
    }

    public void setOutNotify(OutNotify outNotify) {
        outNotify.addPropertyChangeListener(this);
        this.outNotify = outNotify;
    }
    
    public OutControl(){}
    
     @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(OutNotify.OUT_FILE_NAME)){
           String path = (String) evt.getNewValue();
           action(path);             
        }
   
    }    
    protected abstract void action(String  outPath);
}
