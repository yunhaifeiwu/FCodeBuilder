/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.junit.Test;

/**
 *
 * @author cloud
 */
public class ClassB<T> {
    private Class <T> cls;
    public ClassB(){
     
        this.cls = (Class<T>) ((ParameterizedType) 
                getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        System.out.println(cls);
    }
    @Test
    public void dd(){
        ClassB<String> a= new ClassB<String>() {};
        System.out.println();
    }
}

