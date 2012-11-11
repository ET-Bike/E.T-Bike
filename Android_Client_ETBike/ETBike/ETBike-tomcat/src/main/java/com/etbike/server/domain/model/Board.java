
package com.etbike.server.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.etbike.server.support.utils.TimeUtils;

@Entity
public class Board extends AbstractPersistable<Long>{
	private static final long serialVersionUID = -3357089862684971998L;

	@ManyToOne
	private Account account;
	private String title;
	@Column(columnDefinition="LONGTEXT") 
	private String content;
	private String writer;
	private BoardCategory category;
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
	private Date updatedTime;
	
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private Set<Reply> replies;
	
	public void setId(Long id){
		super.setId(id);
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public BoardCategory getCategory() {
		return category;
	}
	public void setCategory(BoardCategory category) {
		this.category = category;
	}
	public void setCategoryValue(String value) {
		try {
			this.category = BoardCategory.valueOf(value.toUpperCase());
		} catch (Exception e) {
			this.category = BoardCategory.NOTICE;
		}
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getUpdatedTimestamp(){
		Date date = getUpdatedTime();
		return date != null ?
				TimeUtils.getTimeStamp(date.getTime())
				: "";
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
