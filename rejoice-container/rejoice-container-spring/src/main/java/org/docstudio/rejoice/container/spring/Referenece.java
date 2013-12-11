package org.docstudio.rejoice.container.spring;

public class Referenece<T> implements ReferenceBean<T> {
	
	private Class<T> interfaceClass;
	
	private T instance = null;
	
	private JDKProxy<T> proxy = null;
	
	public void init(){
		proxy = new JDKProxy<T>();
		instance = proxy.createProxy(interfaceClass);
	}

	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}
	
	public T getInstance(){
		return instance;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	
}
