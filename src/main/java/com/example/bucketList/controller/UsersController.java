package com.example.bucketList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bucketList.entity.Task;
import com.example.bucketList.entity.User;
import com.example.bucketList.entity.User.Authority;
import com.example.bucketList.factory.TaskFactory;
import com.example.bucketList.form.UserForm;
import com.example.bucketList.repository.TaskRepository;
import com.example.bucketList.repository.UserRepository;

@Controller
public class UsersController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;

	@GetMapping("/users/new")
	public String newUser(Model model) {
		model.addAttribute("form", new UserForm());
		return "users/new";
	}

	@PostMapping("/user")
	public String create(@Validated @ModelAttribute("form") UserForm form, BindingResult result, Model model,
			RedirectAttributes redirAttrs) {
		String email = form.getEmail();
		String password = form.getPassword();

		if (userRepository.findByUsername(email) != null) {
			FieldError fieldError = new FieldError(result.getObjectName(), "email", "その E メールはすでに使用されています。");
			result.addError(fieldError);
		}
		if (result.hasErrors()) {
			return "users/new";
		}

		User entity = new User(email, passwordEncoder.encode(password), Authority.ROLE_USER);
		userRepository.saveAndFlush(entity);
		return "sessions/new";
	}

	@GetMapping("/pages/main")

	public String mainPage(Model model) {
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model);
		return "/pages/main";

	}

	//	タスクを登録する
	@PostMapping("/path")
	public String taskCreate(@ModelAttribute("form") Task form, BindingResult result,
			Model model) {
		taskRepository.saveAndFlush(TaskFactory.createTask(form));
		model.addAttribute("form", TaskFactory.newTask());
		model = this.setList(model);

		return "redirect:/pages/main";
	}

	private Model setList(Model model) {
		List<Task> list = taskRepository.findAll();
		model.addAttribute("list", list);
		return model;
	}
}
