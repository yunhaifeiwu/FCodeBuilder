/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.match;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author cloud
 */
public class MatchNotify {
    public static final String MATCH_DATA="matchData";
    private ConcurrentHashMap<String,MatchData> map=new ConcurrentHashMap ();
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public ConcurrentHashMap<String, MatchData> getMap() {
        return map;
    }

    public void setMap(ConcurrentHashMap<String, MatchData> map) {
        this.map = map;
    }

         
    
    public void addPropertyChangeListener (PropertyChangeListener listener) {
          changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener (PropertyChangeListener listener) {
        changes.removePropertyChangeListener (listener);
    }

    /**
     * 投递匹配的数据，产生事件。
     * @param md 待投递的数据
     */
    public void postMatchData(MatchData md){
        changes.firePropertyChange(MATCH_DATA, map.get(md.getTemplate()), md);
        map.put(md.getTemplate(), md);
        
    }
    
   
    
}
