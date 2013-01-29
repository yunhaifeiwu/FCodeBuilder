/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.engine;

import fcodebuilder.match.MatchData;
import fcodebuilder.match.MatchNotify;
import fcodebuilder.out.OutControl;
import fcodebuilder.out.OutNotify;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cloud
 */
public abstract class CodeEngine implements PropertyChangeListener  {
    private MatchNotify matchNotify;
    private OutNotify outNotify;    
    private OutControl outControl;
    
    public MatchNotify getMatchNotify() {
        return matchNotify;
    }

    public void setMatchNotify(MatchNotify matchNotify) {
        setMatchNotify1(matchNotify);
    }

    public OutNotify getOutNotify() {
        return outNotify;
    }

    public void setOutNotify(OutNotify outNotify) {
        this.outNotify = outNotify;
    }

    public OutControl getOutControl() {
        return outControl;
    }

    public void setOutControl(OutControl outControl) {
        this.outControl = outControl;
    }
       
    
    private void setMatchNotify1(MatchNotify matchNotify) {
        matchNotify.addPropertyChangeListener(this);
        this.matchNotify = matchNotify;
    }
    
    public CodeEngine(){}
    public CodeEngine(MatchNotify matchNotify){
        setMatchNotify1(matchNotify);
    }
  
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(MatchNotify.MATCH_DATA)){
           MatchData md= (MatchData) evt.getNewValue();
           action(md);
           if(outNotify!=null){outNotify.postOutFileName(md.getOutPath());}            
        }
   
    }
    
    
    protected abstract void action(MatchData matchData);
}
    
