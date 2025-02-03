package com.team4.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.taskmanager.model.Role;
import com.team4.taskmanager.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService{
	@Autowired
    private RoleRepository roleRepository;
  
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

