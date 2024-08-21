package com.example.demo.service;

import com.example.demo.UserDao;
import com.example.demo.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserData RegisterUser(UserData user){
        if(userDao.findByUserName(user.getUserName())!=null){
            throw new RuntimeException("Username is already taken. Please choose a different one");
        }
        return userDao.save(user);
    }

    @Override
    public UserData loginUser(String userId, String password) {
        UserData user = userDao.findByUserName(userId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new RuntimeException("Invalid username or password.");
    }

    @Override
    public List<UserData> getAllUsers() {
        List<UserData> users = userDao.findAll();
        // Optionally clear the password field
        users.forEach(user -> user.setPassword(null));
        return users;
    }

}
