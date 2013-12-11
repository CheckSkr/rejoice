package org.docstudio.rejoice.container.spring.support;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ReferenceNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser());  
	}

}
