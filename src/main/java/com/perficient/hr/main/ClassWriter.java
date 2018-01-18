package com.perficient.hr.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.form.NotificationMail;
import com.perficient.hr.model.Notification;
import com.perficient.hr.service.MailService;
import com.perficient.hr.service.impl.EmployeeLeavesServiceImpl;
import com.perficient.hr.utils.PerfHrConstants;


public class ClassWriter implements ItemWriter<Notification> {

	@Autowired
	EmployeeLeavesServiceImpl iEmployeeLeavesServiceImpl;
	
	@Autowired
    MailService mailService;
	
	@Override
	public void write(List<? extends Notification> notifications) throws Exception {
		Notification notification = null;
		List<String> recepientList =  new ArrayList<String>();
		if(notifications != null){
			for (Iterator<? extends Notification> iterator = notifications.iterator(); iterator.hasNext();) {
				try {
					notification = (Notification) iterator.next();
					recepientList.add("S.Chinnamuthu@perficient.com");
					//recepientList.add(notification.getNotificationTo().getEmail());
					//NotificationMail notificationMail = setNotificationMail(notification,recepientList);
					//mailService.sendNotificationMail(notificationMail);
					System.out.println("Mail was send successfully");
					notification.setMailStatus(PerfHrConstants.SUCCESS);
					iEmployeeLeavesServiceImpl.updateNotification(notification);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Inside ClassWriter..." + notifications.size());	
		}
	}
	
	/**
	 * @param recepientList 
	 * @param employeeLeaves
	 * @param recipientList
	 * @return
	 */
	private NotificationMail setNotificationMail(Notification notification, List<String> recepientList){
		NotificationMail notificationMail = new NotificationMail();
		notificationMail.setCalendar(true);
		//notificationMail.setSummary(employeeLeaves.getTitle());
		notificationMail.setUid(notification.getPk().toString());
		notificationMail.setDescription(notification.getComments());
		notificationMail.setDateStart(notification.getDtCreated());
		notificationMail.setMsgBody(notification.getComments());
		notificationMail.setRecipientsList(recepientList);
		return notificationMail;
	}
}
