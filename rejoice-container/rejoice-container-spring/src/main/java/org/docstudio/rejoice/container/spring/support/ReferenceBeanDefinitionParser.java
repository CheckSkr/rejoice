package org.docstudio.rejoice.container.spring.support;

import org.docstudio.rejoice.container.spring.Referenece;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/***
 * 
 * @author Winston
 *
 */
public class ReferenceBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser { 
	
	private Class interfaceClass = null;
		  
	    @Override  
	    protected void doParse(Element element, ParserContext parserContext,  
	            BeanDefinitionBuilder builder) {  
	    	String interfaceStr = element.getAttribute("interface");
	    	try {
				interfaceClass = Class.forName(interfaceStr);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("can not find interface:"+interfaceStr+" in current classloader");
			}
	    	
	    	builder.genericBeanDefinition(Referenece.class);
	    	builder.addPropertyValue("interfaceClass", interfaceClass);
	    	
	    	builder.setInitMethodName("init");
	    }  
	    @Override  
	    protected Class getBeanClass(Element element) {  
	        return Referenece.class;  
	    }  

}
