package com.springboot.sonice.myFirstWebApp.todo;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.sonice.myFirstWebApp.repository.TodoRepository;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoJpaController {
	private TodoRepository todoRepository;

	public TodoJpaController(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		var userName = getLoggedInUserName(model);
		var toDo = todoRepository.findByUserName(userName);
		model.addAttribute("todos", toDo);
		return "listDo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String showToDO(ModelMap model) {
		var userName = getLoggedInUserName(model);
		var toDo = new Todo(0, userName, "", LocalDate.now().plusYears(1), false);
		model.put("toDo", toDo);
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String addNewToDo(ModelMap model, @Valid @ModelAttribute("toDo") Todo toDo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		var userName = getLoggedInUserName(model);
		toDo.setUserName(userName);
		todoRepository.save(toDo);
		return "redirect:list-todos";
	}

	@RequestMapping("delete-todo")
	public String delete(@RequestParam int id) {
		todoRepository.deleteById(id);
		return "redirect:list-todos";
	}

	@RequestMapping(value = "update-todo", method = RequestMethod.GET)
	public String showToDoPage(@RequestParam int id, ModelMap model) {
		var toDo = todoRepository.findById(id).get();
		// The name of within "toDo" should be matching with the model attribute jsp
		// file
		model.addAttribute("toDo", toDo);
		return "todo";
	}

	@RequestMapping(value = "update-todo", method = RequestMethod.POST)
	public String updateToDo(ModelMap model, @Valid @ModelAttribute("toDo") Todo toDo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";

		}
		var userName = getLoggedInUserName(model);
		toDo.setUserName(userName);
		todoRepository.save(toDo);
		return "redirect:list-todos";
	}

	private String getLoggedInUserName(ModelMap model) {
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		return authenticate.getName();
	}

}
