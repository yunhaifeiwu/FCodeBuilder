/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.in;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cloud
 */
public abstract class ReadSource {
    public final static int TYPE_TEMPLET=0;
    public final static int TYPE_TEMPLET_DATA=1;
    
    protected int type=TYPE_TEMPLET;
    
    private LinkedList<FileInfo> list=new LinkedList<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    public LinkedList<FileInfo> getList() {
        return list;
    }

    public void setList(LinkedList<FileInfo> list) {
        this.list = list;
    }
    
    
    
    public void addFileInf(String filename){
        list.add(new FileInfo(filename));
    }
    
    public List<FileInfo> getFileInfos(){
       return getFileInfosImpl();
    }
    public abstract List<FileInfo> getFileInfosImpl();
    
    
}
