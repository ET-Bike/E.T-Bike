
package com.etbike.server.domain.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.etbike.server.support.utils.TimeUtils;

@Entity
public class Board extends AbstractPersistable<Long>{
	private static final long serialVersionUID = -3357089862684971998L;

	private String title;
	@Column(columnDefinition="LONGTEXT") 
	private String content;
	private String writer;
	private BoardCategory category;
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
	private Date updatedTime;
	
	private String myImagePath;
	private String bikeImagePath;
	private String bikeType;
	private String tradeType;
	private String shareType;
	private String lati;
	private String longi;
	private String costPerTime;
	private String costPerDay;
	private String costPerWeek;
	private String like;          
	private String  dealWith;          
	private String bikeImagePathThumb;
	
	public Board() {}
	public Board(String title, String content,String writer,BoardCategory category
			 ,Date updatedTime,String myImagePath,String bikeImagePath
			 ,String bikeType,String tradeType,String shareType,String lati,String longi,String costPerTime
			 ,String costPerDay,String costPerWeek) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.category = category;
		this.updatedTime = updatedTime;
		this.myImagePath = myImagePath;
		this.bikeImagePath = bikeImagePath;
		this.bikeType = bikeType;
		this.tradeType = tradeType;
		this.shareType = shareType;
		this.lati = lati;
		this.longi = longi;
		this.costPerTime = costPerTime;
		this.costPerDay = costPerDay;
		this.costPerWeek = costPerWeek;
	}
	
	public String getLike() {
		return like;
	}
	public void setLike(String like) {
		this.like = like;
	}
	public String getDealWith() {
		return dealWith;
	}
	public void setDealWith(String dealWith) {
		this.dealWith = dealWith;
	}
	public String getBikeImagePathThumb() {
		return bikeImagePathThumb;
	}
	public void setBikeImagePathThumb(String bikeImagePathThumb) {
		this.bikeImagePathThumb = bikeImagePathThumb;
	}
	public String getMyImagePath() {
		return myImagePath;
	}
	public void setMyImagePath(String myImagePath) {
		this.myImagePath = myImagePath;
	}
	public String getBikeImagePath() {
		return bikeImagePath;
	}
	public void setBikeImagePath(String bikeImagePath) {
		this.bikeImagePath = bikeImagePath;
	}
	public String getBikeType() {
		return bikeType;
	}
	public void setBikeType(String bikeType) {
		this.bikeType = bikeType;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getLati() {
		return lati;
	}
	public void setLati(String lati) {
		this.lati = lati;
	}
	public String getLongi() {
		return longi;
	}
	public void setLongi(String longi) {
		this.longi = longi;
	}
	public String getCostPerTime() {
		return costPerTime;
	}
	public void setCostPerTime(String costPerTime) {
		this.costPerTime = costPerTime;
	}
	public String getCostPerDay() {
		return costPerDay;
	}
	public void setCostPerDay(String costPerDay) {
		this.costPerDay = costPerDay;
	}
	public String getCostPerWeek() {
		return costPerWeek;
	}
	public void setCostPerWeek(String costPerWeek) {
		this.costPerWeek = costPerWeek;
	}
	public Set<Reply> getReplies() {
		return replies;
	}
	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}
////
	
	
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private Set<Reply> replies;
	
	public void setId(Long id){
		super.setId(id);
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
			this.category = BoardCategory.HOME;
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
