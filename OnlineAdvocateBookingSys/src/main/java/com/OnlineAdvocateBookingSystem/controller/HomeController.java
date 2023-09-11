package com.OnlineAdvocateBookingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.OnlineAdvocateBookingSystem.dao.UserRepository;
import com.OnlineAdvocateBookingSystem.entities.User;
import com.OnlineAdvocateBookingSystem.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;
	//@Autowired
	//private BCryptPasswordEncoder passwordEncoder;
	//@Autowired
	//private com.smartcontactmanager.dao.UserRepository userRepository; 
@RequestMapping("/")
public String home(Model model){
model.addAttribute("title","This is home page");
return "home";
}
@RequestMapping("/about")
public String about(Model model){
	model.addAttribute("title","SmartContact");
	return "about";
}
@RequestMapping("/forgotpassword")
public String forgotpassword(Model model){
	model.addAttribute("title","SmartContact");
	return "forgotpassword";
}

@RequestMapping("/log")
public String log(Model model){
	model.addAttribute("title","SmartContact");
	return "log";
}
@ModelAttribute
@RequestMapping("/registration")
public String registration(Model model){
model.addAttribute("title","SmartContact");
	model.addAttribute("user",new User());
	return "registration";
	//handler for registering user	 
}
@RequestMapping("/videocall")
public String videocall(Model model){
	model.addAttribute("title","SmartContact");
	return "videocall";
}
@RequestMapping("/searchbar")
public String searchbar(Model model){
	model.addAttribute("title","SmartContact");
	return "searchbar";
}
@RequestMapping("/signup")
public String signup(Model model){
model.addAttribute("title","SmartContact");
	model.addAttribute("user",new User());
	return "signup";
}
@RequestMapping(value="/do_register", method = RequestMethod.POST)
public String registerUser(@ModelAttribute("user")User user,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,HttpSession session){
	try {
		if(!agreement) {
			System.out.println("You have not agreed the terms and condition..!!");
			throw new Exception("You have not agreed the terms and condition..!!");
		}
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("default.png");
		System.out.println("Agreement "+agreement);
		System.out.println("USER"+user);
		User result = this.userRepository.save(user);
		model.addAttribute("user", new User());
		session.setAttribute("message", new Message("Successfully registered..!!","alert-success"));
		return "registration";
	}catch(Exception e) {
		e.printStackTrace();
		model.addAttribute("user",user);
		session.setAttribute("message", new Message("Something wents wrong..!!"+e.getMessage(),"alert-danger"));
		return "registration";
	}
}
}

