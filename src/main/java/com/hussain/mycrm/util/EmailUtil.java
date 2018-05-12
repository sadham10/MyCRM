package com.hussain.mycrm.util;

import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailUtil {
	
private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Value("${report.fromAddress}")
	private String fromAddress;
	
	public void sendEmail(String subject,String messageBody,String[] toAddresses) {
		sendEmail(subject,messageBody,null,toAddresses);
	}
	
	public void sendEmail(String subject, String message, Map<String, byte[]> attachments, String[] toAddresses) {
		int retryCount = 0;
		do {
			try {
				retryCount++;
				MimeMessage mail = emailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mail, true);
				helper.setTo(toAddresses);
				helper.setFrom(fromAddress);
				helper.setSubject(subject);
				helper.setText(message, true);
				if (attachments != null) {
					Set<String> keyset = attachments.keySet();
					for (String str : keyset) {
						byte[] report = attachments.get(str);
						str += ".xlsx";
						helper.addAttachment(str, new ByteArrayResource(report));
					}
				}
				emailSender.send(mail);
				return ;
			} catch (MessagingException e) {
				LOGGER.error("Error while sending email", e);
				if(retryCount >= 2) {
					LOGGER.info("Error while sending email");
				}
			}
			LOGGER.warn("Retrying to send again, Retry Count:"+retryCount);
		} while (retryCount < 2);
	}

}
