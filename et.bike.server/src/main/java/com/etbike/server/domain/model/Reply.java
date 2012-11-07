
package com.etbike.server.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.etbike.server.support.utils.TimeUtils;

@Entity
public class Reply extends AbstractPersistable<Long>{
	private static final long serialVersionUID = 7250970238230831841L;
	 
	private String message;
	private String writer;
	@JsonIgnore
	@ManyToOne
	private Board board;
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
	private Date updatedTime;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
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
