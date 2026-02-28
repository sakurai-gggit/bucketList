package com.example.bucketList.factory;

import java.util.Date;

import com.example.bucketList.entity.Task;
import com.example.bucketList.entity.User;

public class TaskFactory {

	private TaskFactory() {

	}

	//空白のタスクを生成する
	public static Task newTask() {
		Task task = new Task();
		return task;
	}

	//	入力内容を設定したタスクを生成する
	public static Task createTask(Task task, User user) {
		Date current = new Date();
		task.setCreatedDate(current);
		task.setUser(user);
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
