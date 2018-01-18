package com.perficient.hr.main;

import org.springframework.batch.item.ItemProcessor;

import com.perficient.hr.model.Notification;

public class ClassProcessor  implements ItemProcessor<Notification, Notification>{

	@Override
	public Notification process(Notification notification) throws Exception {
	    return notification;
	}

}