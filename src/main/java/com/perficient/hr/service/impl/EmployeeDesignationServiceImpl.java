package com.perficient.hr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDesignationDAO;
import com.perficient.hr.form.JobTitle;
import com.perficient.hr.service.EmployeeDesignationService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;

@Repository("employeeDesignationService")
public class EmployeeDesignationServiceImpl implements EmployeeDesignationService{

protected Logger logger = LoggerFactory.getLogger(EmployeeDesignationServiceImpl.class);

	@Autowired
	private EmployeeDesignationDAO employeeDesignationDAOImplDAO;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
	@Override
	public Object loadBySbu(String stDate, String endDate,String sbu, String designation) {
		LoggerUtil.infoLog(logger, "Loading employee List and Designation with the Date Range : " + sbu);
		Session session = null;
		List<JobTitle> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDesignationDAOImplDAO.loadBySbu(stDate, endDate, sbu, designation, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee List and Designation with the Date Range : "+ sbu , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee List and Designation with the Date Range : "+ sbu , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}
}
