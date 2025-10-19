package com.springboot.sonice.myFirstWebApp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.sonice.myFirstWebApp.security.SpringSecurityConfiguration;

@Controller
@SessionAttributes("name")
public class WelcomeController {
	
//	private Logger logger=LoggerFactory.getLogger(WelcomeController.class);
	//private AuthenticateService authenticate; 
	
//	public LoginController( AuthenticateService authenticate) {
//		super();
//		this.authenticate = authenticate;
//	}

	// http://localhost:8081:login?name=sonica
//	@RequestMapping("/login")
//	public String login(@RequestParam String name, ModelMap model) {
//		model.put("name", name);
//		logger.debug("Request param is : {}" ,name);
//		return "login";
//	}
//	

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String goToWelcome(ModelMap model) {
		model.put("name", getLoggedInUserName());
		return "welcome";
	}
	
//	@RequestMapping(value="/login-details", method=RequestMethod.POST)
//	public String goToWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap model) {
//		if(authenticate.authenticate(name, password)) {
//		model.put("name", name);
//		
//		//Authenticate add user name and password
//		return "welcome";
//		}
//		model.put("errorMessage", "Invalid credentials! please try again");
//		return "login";
//	}
	
	private String getLoggedInUserName() {
		//Whoever authenticated user we can get through this
		Authentication authenticate=SecurityContextHolder.getContext().getAuthentication();
		return authenticate.getName();
	}
}
