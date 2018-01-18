package com.perficient.hr.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.model.Notification;
import com.perficient.hr.service.impl.EmployeeLeavesServiceImpl;

public class ClassReader implements ItemReader<Notification> {

	public Queue<Notification> notificationList = null;

	@Autowired
	private EmployeeLeavesServiceImpl iEmployeeLeavesServiceImpl;

	@Override
	public Notification read() throws Exception, UnexpectedInputException,ParseException, NonTransientResourceException {
		Notification notification = notificationList.poll();
		return notification;
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		List<Notification> notificationAsList = (List<Notification>) iEmployeeLeavesServiceImpl.getNotificationDetails();
		notificationList =  new LinkedList<Notification>(notificationAsList);
	}

}
