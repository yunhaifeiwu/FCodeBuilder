/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.match;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cloud
 */
public class AcceptTest implements PropertyChangeListener  {
    public AcceptTest(MatchNotify mn){
        mn.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(MatchNotify.MATCH_DATA)){
           MatchData md= (MatchData) evt.getNewValue();
           md.getTemplateData().put("ddd", "AcceptTest");
        }
   
    }
}
