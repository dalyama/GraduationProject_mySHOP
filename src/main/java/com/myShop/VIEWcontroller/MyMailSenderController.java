package com.myShop.VIEWcontroller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myShop.entities.MailRequest;
import com.myShop.entities.MailResponse;
import com.myShop.services.EmailService;


@RequestMapping("/mail")
@RestController
public class MyMailSenderController {

	@Autowired
    private EmailService emailService;
	

	private String from = "adswallsystem@gmail.com";
    @GetMapping(value = "/sendmail")
    public MailResponse sendEmail(MailRequest request) {

    	request.setFrom(this.from);
    	
    	Map<String,Object> model = new HashMap<>();
    	model.put("name", request.getName());
    	model.put("code", request.getContent());
    	model.put("location","Syria");
    	
    	return emailService.sendEmail(request, model);
    }
    
    
    
    
}
