package org.hashcode.minitabor.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailService {
	public static final Logger LOG = Logger.getLogger(MailService.class);

	public static boolean competitionSend() throws UnsupportedEncodingException, MessagingException {
		LOG.info("Zkusím rozesílat e-mail");

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("zigisps@gmail.com", "Example.com Admin"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("zigisps@gmail.com", "Mr. User"));
		msg.setSubject("Your Example.com account has been activated");
		msg.setText("This is a test");
		Transport.send(msg);
		return true;
	}
}
