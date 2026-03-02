package com.example.bucketList.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bucketList.entity.Task;
import com.example.bucketList.entity.User;
import com.example.bucketList.factory.TaskFactory;
import com.example.bucketList.repository.TaskRepository;
import com.example.bucketList.repository.UserRepository;

@Controller
public class TasksController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;

	//	タスクを登録する
	@PostMapping("/path")
	public String taskCreate(@ModelAttribute("form") Task form, BindingResult result,
			Model model, Principal principal) {
		String email = principal.getName();
		User currentUser = userRepository.findByUsername(email);
		taskRepository.saveAndFlush(TaskFactory.createTask(form, currentUser));
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model);

		return "redirect:/pages/main";
	}

	//	タスクを達成済み、未達成にする
	@PostMapping("/completed")
	public String toggleTask(@RequestParam Long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
		task.setCompleted(!task.isCompleted());
		taskRepository.save(task);
		return "redirect:/pages/main";
	}

	@GetMapping("/pages/main")
	public String mainPage(Model model) {
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model);
		return "pages/main";

	}

	private Model setList(Model model) {
		List<Task> list = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "taskId"));
		model.addAttribute("list", list);
		return model;
	}

}
