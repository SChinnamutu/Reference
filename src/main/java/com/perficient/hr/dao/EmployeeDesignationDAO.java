package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.form.JobTitle;

public interface EmployeeDesignationDAO {

	public List<JobTitle> loadBySbu(String stDate, String endDate,String sbu,String desingation, Session session)
			throws GenericException;

}
