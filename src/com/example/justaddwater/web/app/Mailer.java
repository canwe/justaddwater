package com.example.justaddwater.web.app;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.wicket.util.string.Strings;

public class Mailer {

	public static final Logger log = Logger.getLogger(Mailer.class.getName());

    public static void sendEmail(String from, String fromName, String subject, String body, String to, String bcc)
    {
    	
    	Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        try
        {
            //Construct the data
        	Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, fromName));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(body);
            if (! Strings.isEmpty(bcc))
            {
            	msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
                
            }
            Transport.send(msg);
        }
        catch (Exception e)
        {
        	log.warning("Exception while send mail: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
