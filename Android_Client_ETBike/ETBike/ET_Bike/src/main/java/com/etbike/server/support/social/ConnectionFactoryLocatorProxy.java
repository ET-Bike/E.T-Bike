package com.etbike.server.support.social;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;

public class ConnectionFactoryLocatorProxy implements ConnectionFactoryLocator, Serializable, BeanFactoryAware {
	private static final long serialVersionUID = 5581887258582146941L;

	private BeanFactory beanFactory;
	private final String targetBeanName;

	public ConnectionFactoryLocatorProxy(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public <A> ConnectionFactory<A> getConnectionFactory(Class<A> apiType) {
		return getTargetBean().getConnectionFactory(apiType);
	}

	@Override
	public ConnectionFactory<?> getConnectionFactory(String providerId) {
		return getTargetBean().getConnectionFactory(providerId);
	}

	@Override
	public Set<String> registeredProviderIds() {
		return getTargetBean().registeredProviderIds();
	}

	private ConnectionFactoryLocator getTargetBean() {
		return (ConnectionFactoryLocator) beanFactory.getBean(targetBeanName);
	}
}