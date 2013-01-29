/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.in;

/**
 *
 * @author cloud
 */
public class FileInfo {
    public FileInfo(){ }
    public FileInfo(String filename){
        this.name=filename;
    }
    private String name;    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
