package com.exe.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.exe.smartcontact.dao.UserRepository;
import com.exe.smartcontact.entities.User;
import com.exe.smartcontact.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void someMethod() {
		String encodedPassword = passwordEncoder.encode("yourPassword");
		System.out.println("Encoded Password: " + encodedPassword);
	}
	// @RequestMapping("/test")
	// @ResponseBody
	// public String test() {
	//
	// User user = new User();
	// user.setName("Durgesh Tiwari");
	// user.setEmail("lucky@dev.io");
	//
	// userepo.save(user);
	// return "wellcome";
	// }
	//

	@RequestMapping("/login")
	public String customlogin(Model model) {

		model.addAttribute("title", "Login - Smart Contact Manager");
		return "login";
	}

	@RequestMapping("/login-fail")
	public String customloginerror(Model model) {

		model.addAttribute("title", "Login-error : Smart Contact Manager");
		return "login-fail";
	}

	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "home - smart contact manager");
		return "home";

	}

	@RequestMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "about - smart contact manager");
		return "about";

	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("title", "signup - smart contact manager");
		return "signup";

	}

	// @Transactional
	@PostMapping("/do_register")
	public String reg(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {

			if (!agreement) {
				System.out.println("you have not accept the terms and condition");
				throw new Exception("you have not accept the terms and condition");
			}

			if (result1.hasErrors()) {
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			User result = this.userepo.save(user);

			System.out.println("agreement" + agreement);
			System.out.println("USER" + result);
			session.setAttribute("message", new Message("Succefully Registered !!", "alert-success"));
			return "redirect:/login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("something went wrong!! " + e.getMessage(), "alert-danger"));

			return "signup";
		}

	}

}