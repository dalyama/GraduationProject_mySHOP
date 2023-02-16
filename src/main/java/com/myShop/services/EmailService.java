package com.myShop.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.myShop.entities.MailRequest;
import com.myShop.entities.MailResponse;
import com.myShop.entities.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.var;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration config;

	public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
		System.out.println("Start EmailService.sendEmail()");
		MailResponse response = new MailResponse();
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			
			//add Attachment
			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
			
			Template template = config.getTemplate("email-template.html");
			String html =  FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			helper.setTo(request.getTo());
			helper.setFrom(request.getFrom());
			helper.setSubject(request.getSubject());
			helper.setText(html,true);
			
			javaMailSender.send(message);
			response.setMessage("Mail send to : "+request.getTo());
			response.setStatus(Boolean.TRUE);
			System.out.println("Start EmailService.sendEmail()");
		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail sending failure "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}

	public void sendMail(MailRequest mail) throws MailException {

		var mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getTo());
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getContent());
		mailMessage.setFrom(mail.getFrom());

		javaMailSender.send(mailMessage);
	}
}
