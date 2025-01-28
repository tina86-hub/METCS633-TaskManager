package com.team4.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.taskmanager.model.Task;
import com.team4.taskmanager.model.User;
import com.team4.taskmanager.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService{
	@Autowired
	private TaskRepository taskRepository;

	public void createTask(Task task) {
		taskRepository.save(task);
	}

	public void updateTask(Long id, Task updatedTask) {
		Task task = taskRepository.getOne(id);
		task.setName(updatedTask.getName());
		task.setDescription(updatedTask.getDescription());
		task.setDate(updatedTask.getDate());
		taskRepository.save(task);
	}

	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
	public List<Task> findAll() {
		return taskRepository.findAll();
	}


	public List<Task> findByOwnerOrderByDateDesc(User user) {
		return taskRepository.findByOwnerOrderByDateDesc(user);
	}

	
	public void setTaskCompleted(Long id) {
		Task task = taskRepository.getOne(id);
		task.setCompleted(true);
		taskRepository.save(task);
	}

	
	public void setTaskNotCompleted(Long id) {
		Task task = taskRepository.getOne(id);
		task.setCompleted(false);
		taskRepository.save(task);
	}

	
	public List<Task> findFreeTasks() {
		return taskRepository.findAll()
				.stream()
				.filter(task -> task.getOwner() == null && !task.isCompleted())
				.collect(Collectors.toList());

	}


	public Task getTaskById(Long id) {
		return taskRepository.findById(id).orElse(null);
	}

	
	public void assignTaskToUser(Task task, User user) {
		task.setOwner(user);
		taskRepository.save(task);
	}

	
	public void unassignTask(Task task) {
		task.setOwner(null);
		taskRepository.save(task);
	}

}
