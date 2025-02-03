package com.team4.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team4.taskmanager.model.Role;
import com.team4.taskmanager.model.Task;
import com.team4.taskmanager.model.User;
import com.team4.taskmanager.repository.RoleRepository;
import com.team4.taskmanager.repository.TaskRepository;
import com.team4.taskmanager.repository.UserRepository;

import java.util.*;

@Service
public class UserService{
	private static final String ADMIN="ADMIN";
	private static final String USER="USER";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByRole(USER);
		user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
		return userRepository.save(user);
	}

	
	public User changeRoleToAdmin(User user) {
		Role adminRole = roleRepository.findByRole(ADMIN);
		user.setRoles(new ArrayList<>(Collections.singletonList(adminRole)));
		return userRepository.save(user);
	}

	
	public List<User> findAll() {
		return userRepository.findAll();
	}



	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	
	public boolean isUserEmailPresent(String email) {
		return userRepository.findByEmail(email) != null;
	}


	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	
	public void deleteUser(Long id) {
		User user = userRepository.getOne(id);
		user.getTasksOwned().forEach(task -> task.setOwner(null));
		userRepository.delete(user);
	}

}

