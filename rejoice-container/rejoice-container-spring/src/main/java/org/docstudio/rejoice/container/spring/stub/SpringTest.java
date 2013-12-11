package org.docstudio.rejoice.container.spring.stub;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	
	public static void main(String[] args){
		
		File file = new File("META-INF/spring.handlers");
		if(file.exists()){
			System.out.print("sssssssssss");
		}
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"test-context.xml"});  
		
	}

}
