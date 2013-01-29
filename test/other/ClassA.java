/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.junit.Test;

public class ClassA<T> {
	private Class<T> cls;
	public ClassA() {
    
 

           Class.class.isAssignableFrom(cls);
          
		this.cls = (Class<T>) ((ParameterizedType) 
                        getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
		System.out.println(cls);
                        int a=0;
                {a=1;
                a=a+1;
                System.out.println(a);}
	}

	public static void main(String[] args) {
		ClassA<String> a = new ClassA<String>() {
		};
                
                
         
	}
}
