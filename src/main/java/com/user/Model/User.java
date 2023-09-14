package com.user.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document
@Data
//@Getter
//@Setter
@Builder
public class User {

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", about=" + about + ", createdDate="
				+ createdDate + ", ratings=" + ratings + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public User(String userId, String name, String email, String about, Date createdDate, List<Rating> ratings) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.about = about;
		this.createdDate = createdDate;
		this.ratings = ratings;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	private String userId;
	private String name;
	private String email;
	private String about;
	private Date createdDate;

	private List<Rating> ratings=new ArrayList<>();

	

}
