package com.etbike.server.support.social;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

public class UserConnectionRepositoryProxy implements UsersConnectionRepository, Serializable, BeanFactoryAware {
	private static final long serialVersionUID = -2401159561056034966L;

	private BeanFactory beanFactory;
	private final String targetBeanName;

	public UserConnectionRepositoryProxy(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		return getTargetBean().findUserIdsWithConnection(connection);
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		return getTargetBean().findUserIdsConnectedTo(providerId, providerUserIds);
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		return getTargetBean().createConnectionRepository(userId);
	}

	private UsersConnectionRepository getTargetBean() {
		return (UsersConnectionRepository) beanFactory.getBean(targetBeanName);
	}
	
}