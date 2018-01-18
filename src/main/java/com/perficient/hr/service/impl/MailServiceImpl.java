package com.perficient.hr.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perficient.hr.form.NotificationMail;
import com.perficient.hr.service.MailService;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.PerfProperties;

@Service("mailService")
public class MailServiceImpl implements MailService{

	protected Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	public PerfProperties perfProperties;
	
	private Session configureMailSession() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.trust", perfProperties.getHost());
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.user", perfProperties.getUsername());
		props.put("mail.smtp.password", perfProperties.getPassword());
		props.put("mail.smtp.host", perfProperties.getHost());
		props.put("mail.smtp.port", perfProperties.getPort());
		props.setProperty("mail.debug", "true");
		return Session.getDefaultInstance(props, null);
	}
	
	@Override
	public void sendNotificationMail(NotificationMail notificationMail) {
		try {
			 //register the text/calendar mime type
	         MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap)MimetypesFileTypeMap.getDefaultFileTypeMap();
	         mimetypes.addMimeTypes("text/calendar ics ICS");
	         
	         //register the handling of text/calendar mime type
	         MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
	         mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	         
	         Session session = configureMailSession();
	         
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(perfProperties.getUsername()));
	         message.setSubject(notificationMail.getSubject());
	         InternetAddress[] mailAddressTO = new InternetAddress [notificationMail.getRecipientsList().size()];
	         for(int i=0;i<mailAddressTO.length;i++){
	        	 mailAddressTO[i] = new InternetAddress(notificationMail.getRecipientsList().get(i));
	         }
	         message.addRecipients(Message.RecipientType.TO, mailAddressTO);
	         
	         // Create an alternative Multipart
	         Multipart multipart = new MimeMultipart("alternative");

	         //part 1, html text
	         MimeBodyPart descriptionPart = new MimeBodyPart();
	         //Note: even if the content is spcified as being text/html, outlook won't read correctly tables at all
	         // and only some properties from div:s. Thus, try to avoid too fancy content
	         String content = "<font size=\"2\">"+notificationMail.getDescription()+"</font>";
	         descriptionPart.setContent(content, "text/html; charset=utf-8");
	         multipart.addBodyPart(descriptionPart);
	         
	         // Add part two, the calendar
	         BodyPart calendarPart = buildCalendarPart(notificationMail);
	         multipart.addBodyPart(calendarPart);
	         
	         //Put the multipart in message 
	         message.setContent(multipart);
	      
	         // send the message
	         Transport transport = session.getTransport("smtp");
	         transport.connect();
	         transport.sendMessage(message, message.getAllRecipients());
	         transport.close();
		} catch (Exception e) {
			logger.error("Unable to send mail "+notificationMail.getSubject()+" Exception is "+e);
		}
	}
	
	private  BodyPart buildCalendarPart(NotificationMail notificationMail) {
		BodyPart calendarPart = new MimeBodyPart();
		try {
			StringBuilder sb = new StringBuilder();
	       //check the icalendar spec in order to build a more complicated meeting request
	       StringBuilder builder = sb.append("BEGIN:VCALENDAR\n" +
	               "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
	               "VERSION:2.0\n" +
	               "METHOD:"+notificationMail.getRequestType()+"\n" +
	               "BEGIN:VEVENT\n" +
	               "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:s.ellappan@perficient.com\n" +
	               "ORGANIZER:MAILTO:no-reply@perficient.com\n" +
	               "DTSTAMP:"+new Date()+"\n" +
	               "DTSTART:"+DateUtils.getiCalDateFormat().format(notificationMail.getDateStart())+"\n" +
	               "DTEND:"+DateUtils.getiCalDateFormat().format(notificationMail.getDateEnd())+"\n" +
	               "LOCATION:\n" +
	               "TRANSP:OPAQUE\n" +
	               "SEQUENCE:"+notificationMail.getSequence()+"\n" +
	               "STATUS:"+notificationMail.getStatusType()+"\n" +
	               "UID:"+notificationMail.getUid()+"\n" +
	               "CATEGORIES:Meeting\n" +
	               "SUMMARY:"+notificationMail.getSummary()+"\n" +
	               "PRIORITY:5\n" +
	               "CLASS:PUBLIC\n" +
	               "BEGIN:VALARM\n" +
	               "TRIGGER:PT1440M\n" +
	               "ACTION:DISPLAY\n" +
	               "DESCRIPTION:Reminder\n" +
	               "END:VALARM\n" +
	               "END:VEVENT\n" +
	               "END:VCALENDAR");
	
			calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
            calendarPart.setDataHandler(new DataHandler(
	               new ByteArrayDataSource(builder.toString(), "text/calendar;method="+notificationMail.getRequestType()+"")));// very important
		} catch (Exception e) {
			logger.error("Unable to build calendar part and send mail "+notificationMail.getSubject()+" Exception is "+e);
		}
       return calendarPart;
   }
}
