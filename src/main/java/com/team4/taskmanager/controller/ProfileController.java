package com.team4.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.team4.taskmanager.model.Task;
import com.team4.taskmanager.model.User;
import com.team4.taskmanager.service.TaskService;
import com.team4.taskmanager.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {

	@Autowired
    private UserService userService;
	@Autowired
    private TaskService taskService;


    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("tasksOwned", taskService.findByOwnerOrderByDateDesc(user));
        return "views/profile";
    }

    @GetMapping("/profile/mark-done/{taskId}")
    public String setTaskCompleted(@PathVariable Long taskId) {
        taskService.setTaskCompleted(taskId);
        return "redirect:/profile";
    }

    @GetMapping("/profile/unmark-done/{taskId}")
    public String setTaskNotCompleted(@PathVariable Long taskId) {
        taskService.setTaskNotCompleted(taskId);
        return "redirect:/profile";
    }
    
    //REST WEb calls
    
    @GetMapping("/rest/tasks/owned")
    public ResponseEntity<List<?>> getTaskOwned(Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        List<Task> tasksOwned = taskService.findByOwnerOrderByDateDesc(user);
        return ResponseEntity.ok(tasksOwned);
    }

   
    @PutMapping("/rest/tasks/{taskId}/unmark-done")
    public ResponseEntity<Void> restTaskNotCompleted( @PathVariable Long taskId) {
        taskService.setTaskNotCompleted(taskId);
        return ResponseEntity.noContent().build();  // Return 204 No Content, indicating success without content in response
    }
    
    @PutMapping("/rest/tasks/{taskId}/mark-done")
    public ResponseEntity<Void>  restTaskCompleted( @PathVariable Long taskId) {
        taskService.setTaskCompleted(taskId);
        return ResponseEntity.noContent().build();  
        }



}
