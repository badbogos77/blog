package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int Id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob
	private String content;	

	private int count;
	
	@ManyToOne(fetch=FetchType.EAGER) //many : board, one: user
	@JoinColumn(name="userid")
	private User user;  // DB는 오브젝트는 저장할 수 없음, FK, 자바는 오브젝트를 저장할 수 있음
	
	@OneToMany(mappedBy = "board" , fetch=FetchType.EAGER)	
	@OrderBy("id desc")
	@JsonIgnoreProperties({"board","reply"})  // 무한참조방지
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;

}
