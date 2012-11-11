package com.etbike.server.support.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import com.etbike.server.support.social.ConnectionFactoryLocatorProxy;
import com.etbike.server.support.social.UserConnectionRepositoryProxy;

@Configuration
public class SocialConfig {

	@Value("${facebook.clientId}") private String facebookClientId;
	@Value("${facebook.clientSecret}") private String facebookClientSecret;

	@Autowired
	private DataSource dataSource;

	@Bean 
	public ConnectionFactoryLocator connectionFactoryLocator() {
		return new ConnectionFactoryLocatorProxy("connectionFactoryLocatorTarget");
	}
	
	@Bean 
	public ConnectionFactoryLocator connectionFactoryLocatorTarget() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new FacebookConnectionFactory(facebookClientId, facebookClientSecret));
		return registry;
	}

	@Bean 
	public UsersConnectionRepository usersConnectionRepository() {
		return new UserConnectionRepositoryProxy("usersConnectionRepositoryTarget");
	}

	@Bean 
	public UsersConnectionRepository usersConnectionRepositoryTarget() {
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository().createConnectionRepository(authentication.getName());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)	
	public Facebook facebook() {
		Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class);
		return facebook != null ? facebook.getApi() : new FacebookTemplate();
	}
}