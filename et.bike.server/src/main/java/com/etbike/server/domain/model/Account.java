package com.etbike.server.domain.model;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long>{
	private static final long serialVersionUID = 5896590556584893996L;
	
	private String username;
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private String grade;
	private String myImagePath;

	public Account(){}

	public Account(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getMyImagePath() {
		return myImagePath;
	}

	public void setMyImagePath(String myImagePath) {
		this.myImagePath = myImagePath;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@JsonIgnore
	@Override
	public boolean isNew() {
		return super.isNew();
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
