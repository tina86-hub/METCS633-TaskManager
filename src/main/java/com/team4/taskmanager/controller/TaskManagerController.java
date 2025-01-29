package com.team4.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team4.taskmanager.model.Task;
import com.team4.taskmanager.model.User;
import com.team4.taskmanager.service.TaskService;
import com.team4.taskmanager.service.UserService;

@Controller
public class TaskManagerController {
	
	@Autowired
    private UserService userService;
	@Autowired
    private TaskService taskService;
	
	@GetMapping("/login")
    String showLoginForm() {
        return "forms/login";
    }
	
	  @RequestMapping("/")
	    String showIndex() {
	        return "index";
	    }
	    
	    @RequestMapping("/about")
	    String showAboutPage(){
	        return "views/about";
	    }

    @GetMapping("/task/assignment")
    public String showAssigmentForm(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "forms/assignment";
    }

    @GetMapping("/task/assignment/{userId}")
    public String showUserAssigmentForm(@PathVariable Long userId, Model model) {
        model.addAttribute("selectedUser", userService.getUserById(userId));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("freeTasks", taskService.findFreeTasks());
        return "forms/assignment";
    }

    @GetMapping("/task/assignment/assign/{userId}/{taskId}")
    public String assignTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Task selectedTask = taskService.getTaskById(taskId);
        User selectedUser = userService.getUserById(userId);
        taskService.assignTaskToUser(selectedTask, selectedUser);
        return "redirect:/task/assignment/" + userId;
    }

    @GetMapping("/task/assignment/unassign/{userId}/{taskId}")
    public String unassignTaskFromUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Task selectedTask = taskService.getTaskById(taskId);
        taskService.unassignTask(selectedTask);
        return "redirect:/task/assignment/" + userId;
    }

}



