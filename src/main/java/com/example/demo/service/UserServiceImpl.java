package com.example.demo.service;

import com.example.demo.UserDao;
import com.example.demo.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
//    @Autowired
//    public UserServiceImpl(PasswordEncoder passwordEncoder,UserDao userDao) {
//    	this.passwordEncoder = passwordEncoder;
//    	this.userDao = userDao;
//    }

    
    public UserData RegisterUser(UserData user){
        if(userDao.findByUserName(user.getUserName())!=null){
            throw new RuntimeException("Username is already taken. Please choose a different one");
        }
        UserData user1 = new UserData();
        user1.setEmail(user.getEmail());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setUserName(user.getUserName());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user1);
    }

    
//    public UserData loginUser(String userId, String password) {
//        UserData user = userDao.findByUserName(userId);
//        if (user != null && user.getPassword().equals(password)) {
//            return user;
//        }
//        throw new RuntimeException("Invalid username or password.");
//    }

    
    public List<UserData> getAllUsers() {
        List<UserData> users = userDao.findAll();
        // Optionally clear the password field
        users.forEach(user -> user.setPassword(null));
        return users;
    }


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserData user = userDao.findByUserName(userName);
//		 if (user == null) {
//	            throw new UsernameNotFoundException("Invalid User Name or Password");
//	        }
		 if (user!=null){
		  return new org.springframework.security.core.userdetails.User(
	                user.getUserName(),
	                user.getPassword(),
	                // Convert roles to authorities if needed
	               Collections.emptyList());
		 }
		 else {
			 throw new UsernameNotFoundException("Invalid User Name or Password");
		 }
	}

}
