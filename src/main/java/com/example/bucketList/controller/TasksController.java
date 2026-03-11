package com.example.bucketList.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bucketList.entity.Task;
import com.example.bucketList.entity.User;
import com.example.bucketList.factory.TaskFactory;
import com.example.bucketList.form.TaskForm;
import com.example.bucketList.repository.TaskRepository;
import com.example.bucketList.repository.UserRepository;

@Controller
public class TasksController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;

	//	タスクを登録する
	@PostMapping("/add")
	public String taskCreate(@ModelAttribute("form") Task form, BindingResult result,
			Model model, Principal principal, @ModelAttribute TaskForm taskForm) {
		String email = principal.getName();
		User currentUser = userRepository.findByUsername(email);
		taskRepository.saveAndFlush(TaskFactory.createTask(form, currentUser));
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model, taskForm);

		return "redirect:/main";
	}

	//	タスクを編集する
	@PostMapping("/update")
	@ResponseBody
	public ResponseEntity<String> taskUpdate(@RequestParam Long taskId, @RequestParam String body) {
		Task task = getTask(taskId);
		task.setBody(body);
		taskRepository.saveAndFlush(task);
		return ResponseEntity.ok("Success");
	}

	//	タスクを達成済み、未達成にする
	@PostMapping("/completed")
	@ResponseBody
	public String toggleTask(@RequestParam Long taskId) {
		Task task = getTask(taskId);
		task.setCompleted(!task.isCompleted());
		taskRepository.save(task);
		return "ok";
	}

	//	タスクを削除する
	@PostMapping("/delete")
	public String deleteTask(@RequestParam Long taskId) {
		taskRepository.deleteById(taskId);
		return "redirect:/edit";
	}

	@GetMapping("/main")
	public String mainPage(Model model, @ModelAttribute TaskForm taskForm) {
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model, taskForm);
		model.addAttribute("currentOrder", taskForm.getOrder());
		long totalTasks = taskRepository.count();
		long completedTasks = taskRepository.countByCompletedTrue();
		model.addAttribute("totalTasks", totalTasks);
		model.addAttribute("completedTasks", completedTasks);
		return "pages/main";
	}

	@GetMapping("/edit")
	public String editPage(Model model, @ModelAttribute TaskForm taskForm) {
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model, taskForm);
		return "pages/edit";
	}

	@GetMapping("/menu")
	public String menuPage(Model model, @ModelAttribute TaskForm taskForm) {
		model.addAttribute("currentOrder", taskForm.getOrder());
		return "pages/menu";
	}

	private Model setList(Model model, @ModelAttribute TaskForm taskForm) {
		Sort sort;
		String order = taskForm.getOrder();
		if ("status".equals(order)) {
			sort = Sort.by(Sort.Direction.ASC, "completed").and(Sort.by(Sort.Direction.ASC, "taskId"));
		} else {
			sort = Sort.by(Sort.Direction.ASC, "taskId");
		}
		List<Task> list = taskRepository.findAll(sort);
		model.addAttribute("list", list);
		return model;
	}

	private Task getTask(Long taskId) {
		return taskRepository.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));

	}
}
