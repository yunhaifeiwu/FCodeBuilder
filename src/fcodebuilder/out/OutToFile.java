/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcodebuilder.out;

/**
 *
 * @author cloud
 */
public class OutToFile extends OutControl{

    @Override
    protected void action(String outPath) {
        System.out.println(outPath);
    }
    
}
