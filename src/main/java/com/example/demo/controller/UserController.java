package com.example.demo.controller;

import com.example.demo.data.UserData;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserData());
        return "register2";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserData user, BindingResult bindingResults, Model model) {
        if(bindingResults.hasErrors()){
            return "register2";
        }
        try {
            userService.RegisterUser(user);
            model.addAttribute("successMessage", "Registration successful! Please log in.");
            return "register2";  // Return to the same registration page
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register2";
        }
    }


//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("user", new UserData());
//        return "login";
//    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password. Please try again.");
        }
        model.addAttribute("user", new UserData());
        return "login";
    }

//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute("user") UserData user, Model model) {
//        try {
//           // UserData loggedInUser = userService.loginUser(user.getUserName(), user.getPassword());
//        } catch (RuntimeException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "login";
//        }
//        List<UserData> allUsers = userService.getAllUsers();
//        model.addAttribute("users", allUsers);
//        return "user-home";
//    }
    
    @GetMapping("/user/list")
    public String listAllUsers(Model model) {
        List<UserData> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-home";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Redirect to home page
        return "redirect:/login";
    }


}
