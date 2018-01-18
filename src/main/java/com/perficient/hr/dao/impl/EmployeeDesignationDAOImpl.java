package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDesignationDAO;
import com.perficient.hr.exception.GenericException;
import com.perficient.hr.form.JobTitle;
import com.perficient.hr.utils.DateUtils;

@Repository("employeeDesignationDAOImplDAO")
public class EmployeeDesignationDAOImpl implements EmployeeDesignationDAO{

protected Logger logger = LoggerFactory.getLogger(EmployeeDesignationDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JobTitle> loadBySbu(String stDate, String endDate,String sbu, String designation, Session session) throws GenericException{
		String startDt = DateUtils.convertMilliSecondsToStringDate(stDate);
		String endDt = DateUtils.convertMilliSecondsToStringDate(endDate);
		String sqlQuery = "SELECT designation, pk as designationId, sbu, count(*) as employeeCount from"
				+ " (SELECT t.employee_pk, t.designation_pk, t.start_date, d.designation,d.pk, d.sbu FROM ( "
				+ " SELECT employee_pk, max(start_date) AS maxtimestamp"
			    + " FROM employee_designation_history s"
			    + " where (s.start_date >= '"+startDt+"' and s.start_date <= '"+endDt+"')"
				+ " or  ((s.end_date >= '"+startDt+"' and s.end_date <= '"+endDt+"')"
				+ " or (s.start_date >= '"+startDt+"' and s.start_date <= '"+endDt+"' and s.end_date is null)"
				+ " or (s.start_date <= '"+startDt+"' and s.end_date is null))"
				+ " GROUP BY employee_pk"
				+ " ) AS tmax inner join employee_designation_history as t on"
				+ " t.employee_pk = tmax.employee_pk and t.start_date = tmax.maxtimestamp"
				+ " LEFT OUTER JOIN perficient.employee e on e.pk = t.employee_pk"
				+ " LEFT OUTER JOIN designations d on t.designation_pk = d.pk";
		if(sbu != null && !"null".equals(sbu)){
			sqlQuery += " where d.sbu='"+sbu+"'";
		}
		sqlQuery += " GROUP BY t.employee_pk order by t.start_date desc) as a";
		if(sbu != null && !"null".equals(sbu)){
			sqlQuery += " GROUP BY designation ORDER BY  designation ASC;";
		} else {
			sqlQuery += " GROUP BY sbu ORDER BY  designation ASC;";
		}
		
		SQLQuery query = session.createSQLQuery(sqlQuery);
		query.addScalar("designation", new StringType());
		query.addScalar("designationId", new StringType());
		query.addScalar("sbu", new StringType());
		query.addScalar("employeeCount", new StringType());
		query.setResultTransformer(Transformers.aliasToBean(JobTitle.class));
		return (List<JobTitle>)query.list();
	}
	
}
