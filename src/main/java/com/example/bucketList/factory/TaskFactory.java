package com.example.bucketList.factory;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.bucketList.entity.Task;

public class TaskFactory {

	private TaskFactory() {

	}

	//新規のタスクを生成する
	public static Task newTask() {
		Task task = new Task();
		return task;
	}

	//	入力内容を設定したタスクを生成する
	public static Task createTask(Task task) {
		String id = UUID.randomUUID().toString();
		task.setId(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		task.setAuthor(auth.getName());
		Date current = new Date();
		task.setCreatedDate(current);
		return task;
	}

	//	更新内容を設定したタスクを生成する
	public static Task updateTask(Task task, Task form) {

		task.setBody(form.getBody());
		return task;
	}

	//	削除を設定したタスクを生成する
	public static Task deleteTask(Task task) {
		task.setDeleted(true);
		return task;
	}

}
