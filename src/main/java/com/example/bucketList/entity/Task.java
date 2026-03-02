package com.example.bucketList.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

	//	ID
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId = null;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	//	内容
	@Column(length = 300, nullable = false)
	private String body = null;

	//	登録日時
	@Column
	private Date createdDate = null;

	//	削除済
	@Column
	private boolean deleted = false;

	//	達成状況
	@Column
	private boolean completed = false;

}
