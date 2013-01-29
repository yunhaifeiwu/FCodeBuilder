/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.out;

import fcodebuilder.match.MatchData;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author cloud
 */
public class OutNotify {
    public static final String OUT_FILE_NAME="outFileName";
    private ConcurrentLinkedQueue <String> link=new ConcurrentLinkedQueue <> ();
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * 存储了历史通知数据的队列
     */
    public ConcurrentLinkedQueue<String> getLink() {
        return link;
    }

    public void setLink(ConcurrentLinkedQueue<String> link) {
        this.link = link;
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
    public void postOutFileName(String name){
        changes.firePropertyChange(OUT_FILE_NAME,null, name);
        link.add(name);
        
    }
}
