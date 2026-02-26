package com.example.bucketList.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name="tasks")
@Data
public class Task {
	
//	ID
	@Id
	@Column
	private String id =null;
	
//	user
	@Column(nullable=false)
	private String author=null;
	
//	内容
	@Column(length=300,nullable=false)
	private String body=null;
	
//	登録日時
	private Date createdDate = null;
	
//	削除済
	private boolean deleted = false;
	

}
