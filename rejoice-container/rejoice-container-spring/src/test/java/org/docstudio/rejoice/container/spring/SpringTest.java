package org.docstudio.rejoice.container.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	
	public static void main(String[] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"test-context.xml"});  
		
	}

}
