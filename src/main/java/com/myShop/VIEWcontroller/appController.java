 package com.myShop.VIEWcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class appController {

	// hello world
	@GetMapping("")
	public String start() {
		return "redirect:/home";
	}
	
	@GetMapping("offers")
	public String offers() {
		return "offers";
	}
	
	@GetMapping("support")
	public String support() {
		return "support";
	}
	
	@GetMapping("quickHelp")
	public String quickHelp() {
		return "quickHelp";
	}
	
	
	
}
