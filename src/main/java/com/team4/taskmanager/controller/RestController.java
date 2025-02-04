package com.team4.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team4.taskmanager.model.Task;
import com.team4.taskmanager.service.TaskService;
import com.team4.taskmanager.service.UserService;

@Controller
@RequestMapping("/api")
public class RestController {
	
	@Autowired
    private TaskService taskService;
	@Autowired
    private UserService userService;
	
	//tasks rest calls
	 @GetMapping("/tasks/all")
	    public ResponseEntity<?> getAllTasks(){
	        try {
	            return new ResponseEntity<>(
	                    taskService.findAll(),
	                    HttpStatus.OK);
	        } catch (Exception e) {
	            return errorResponse();
	        }
	    }

	    @GetMapping("/task/find/{id}")
	    public ResponseEntity<?> getTask(@PathVariable Long id){
	        try {
	            Task optTask = taskService.getTaskById(id);
	            if (optTask != null) {
	                return new ResponseEntity<>(
	                        optTask,
	                        HttpStatus.OK);
	            } else {
	                return noTaskFoundResponse(id);
	            }
	        } catch (Exception e) {
	            return errorResponse();
	        }
	    }

	    @GetMapping("/task/find")
	    public ResponseEntity<?> getTaskByTitle(@RequestParam String name){
	        try {
	            Task optTask = taskService.getTaskByName(name);
	            if (optTask != null) {
	                return new ResponseEntity<>(
	                        optTask,
	                        HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("No task found with a name: " + name, HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            return errorResponse();
	        }
	    }

	    @PostMapping("/task/create")
	    public ResponseEntity<?> createTask(@RequestBody Task newTask){
	        try {
	        	
	        	 taskService.createTask(newTask);
	            return new ResponseEntity<>(
	                  newTask ,
	                    HttpStatus.CREATED);
	        } catch (Exception e) {
	            return errorResponse();
	        }
	    }
	

	    @PostMapping("/task/update/{id}")
	    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
	        try {
	            Task optTask = taskService.getTaskById(id);
	            if (optTask != null) {
	            	 taskService.updateTask(optTask.getId(), updatedTask);
	                return new ResponseEntity<>(
	                       updatedTask,
	                        HttpStatus.OK);
	            } else {
	                return noTaskFoundResponse(id);
	            }
	        } catch (Exception e) {
	            return errorResponse();
	        }
	    }

	    @DeleteMapping("/task/delete/{id}")
	    public ResponseEntity<?> deleteTask(@PathVariable Long id){
	        try {
	            Task optTask = taskService.getTaskById(id);
	            if (optTask != null) {
	                taskService.deleteTask(optTask.getId());
	                return new ResponseEntity<>(String.format("Task with id: %d was deleted", id), HttpStatus.OK);
	            } else {
	                return noTaskFoundResponse(id);
	            }
	        } catch (Exception e) {
	            return errorResponse();
	        }
	    }

	    private ResponseEntity<String> errorResponse(){
	        return new ResponseEntity<>("Something went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    private ResponseEntity<String> noTaskFoundResponse(Long id){
	        return new ResponseEntity<>("No task found with id: " + id, HttpStatus.NOT_FOUND);
	    }

}
