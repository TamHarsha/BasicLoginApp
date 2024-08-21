package com.example.demo.service;

import java.util.List;

import com.example.demo.data.UserData;

public interface UserService {
	
	 public UserData RegisterUser(UserData user);
	 
	 public UserData loginUser(String userId, String password);
	 
	 public List<UserData> getAllUsers();

}
