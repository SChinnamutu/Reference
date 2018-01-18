package com.perficient.hr.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeLeavesDAO;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLeaveDetails;
import com.perficient.hr.model.EmployeeLeaveMaintenance;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.Notification;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeLeavesDAO")
public class EmployeeLeavesDAOImpl implements EmployeeLeavesDAO {

	protected Logger logger = LoggerFactory.getLogger(EmployeeLeavesDAOImpl.class);

	private String employeeId = "employeeId";
	private String dateBetween = " AND el.startsAt>=:startsAt AND el.endsAt<=:endsAt";
	private String startDate = "-01-01";
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;
	
	@Autowired
	EmployeeDAO employeeDAO;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
    
	@Override
	public void saveLeave(Session session, Row row) throws ParseException{
		String leaveType = row.getCell(12).toString();
        String hours = row.getCell(31).toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        if((leaveType.equals(LeaveType.PTO.getLeaveType()) 
        		|| leaveType.equals(LeaveType.UNPLANNED_PTO.getLeaveType())) && !"0".equals(hours)){
    		String sqlQuery =" from Employee as o where o.employeeId=:employeeId";
    		Query query = session.createQuery(sqlQuery);
    		query.setParameter(employeeId, row.getCell(18).toString());
    		Employee employee = (Employee) query.uniqueResult();
    		if(employee != null) {
    			EmployeeLeaves employeeLeaves = new EmployeeLeaves();
    			employeeLeaves.setEmployeeId(employee.getPk());
    			employeeLeaves.setAppliedById(employee.getPk());
    			employeeLeaves.setRequestType(leaveType);
    			String comments = row.getCell(33).toString();
    			if(comments.length() >= 100)
    				comments = comments.substring(0, 99);
        		employeeLeaves.setComments(comments);
        		employeeLeaves.setTitle(row.getCell(19).toString()+" - "+leaveType);
        		Date dt = sdf.parse("01-"+row.getCell(27).toString());
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(dt);
        		logger.info(row.getCell(19).toString()+" Date "+row.getCell(28).toString()+" month "+(cal.get(Calendar.MONTH)+1));
        		Date leaveDate = DateUtils.getDate(row.getCell(28).toString(), (cal.get(Calendar.MONTH)+1));
        		employeeLeaves.setStartsAt(leaveDate);
        		employeeLeaves.setEndsAt(leaveDate);
        		employeeLeaves.setHours((Math.round(Float.parseFloat(hours)) <= 4) ? 4:8);
        		employeeLeaves.setDtCreated(new Date());
        		employeeLeaves.setDtModified(new Date());
        		employeeLeaves.setCreatedBy(employee.getPk());
        		employeeLeaves.setModifiedBy(employee.getPk());
        		
        		session.save(employeeLeaves);
        		
        		EmployeeLeaveDetails empLeaveDetails = new EmployeeLeaveDetails();
        		empLeaveDetails.setEmployeeLeavesId(employeeLeaves.getPk());
        		empLeaveDetails.setLeaveDate(leaveDate);
        		empLeaveDetails.setHours((Math.round(Float.parseFloat(hours)) <= 4) ? 4:8);
        		empLeaveDetails.setDtCreated(new Date());
        		empLeaveDetails.setDtModified(new Date());
        		empLeaveDetails.setCreatedBy(employee.getPk());
        		empLeaveDetails.setModifiedBy(employee.getPk());
        		saveLeaveDetails(empLeaveDetails, session);
        		
        		/*Employee supervisor = employeeDAO.loadById(String.valueOf(employee.getSupervisor()), session);
        		
        		Notification notification = new Notification();
        		notification.setIdGeneric(employeeLeaves.getPk());
        		notification.setNotificationTo(supervisor);
        		notification.setNotificationStatus(NotificationStatusType.APPROVED.getNotificationStatusType());
        		notification.setNotificationType(NotificationType.PTO.getLeaveType());
        		notification.setDtCreated(new Date());
        		notification.setDtModified(new Date());
        		notification.setCreatedBy(employee.getPk());
        		notification.setModifiedBy(employee.getPk());
        		session.save(notification);*/
    		}
        }
	}
	
	@SuppressWarnings("unchecked")
	private List<EmployeeLeaves> loadEmployeeLeaves(String sqlQuery, String leaveType, 
			Date startsAt, Date endsAt, String employeeId, Session session) {
		Query query = session.createQuery(sqlQuery);
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		if(employeeId != null)
			query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("startsAt", startsAt);
		query.setParameter("endsAt", endsAt);
		return query.list();
	}
	
	@Override
	public List<EmployeeLeaves> loadAllLeaves(String leaveType, String calYear, Session session) throws ParseException{
		String sqlQuery = " from EmployeeLeaves el WHERE el.requestType in (:requestTypes) AND el.active=:active"+ dateBetween;
		return loadEmployeeLeaves(sqlQuery, leaveType, new java.sql.Timestamp(DateUtils.getDate(calYear+startDate).getTime()), 
				new java.sql.Timestamp(DateUtils.getDate(calYear+"-12-31").getTime()), null, session);
	}

	@Override
	public List<EmployeeLeaves> loadMyLeaves(String leaveType, String calYear, String employeeId, Session session) throws ParseException{
		String sqlQuery = " from EmployeeLeaves el WHERE el.requestType in (:requestTypes) AND el.active=:active AND el.employeeId=:employeeId"+dateBetween;
		if(leaveType.equals(LeaveType.PTO.getLeaveType()))
			leaveType = LeaveType.ALL_PTO.getLeaveType();
		return loadEmployeeLeaves(sqlQuery, leaveType, new java.sql.Timestamp(DateUtils.getDate(calYear+startDate).getTime()), 
				new java.sql.Timestamp(DateUtils.getDate(calYear+"-12-31").getTime()), employeeId, session);
	}

	@Override
	public List<EmployeeLeaves> loadLeaveReport(EmployeeLeaves employeeLeaves, Session session){
		String sqlQuery = " from EmployeeLeaves el WHERE el.requestType in (:requestTypes) AND el.active=:active AND el.employeeId=:employeeId"+dateBetween+" order by el.startsAt asc";
		return loadEmployeeLeaves(sqlQuery, employeeLeaves.getRequestType(), new java.sql.Timestamp(employeeLeaves.getStartsAt().getTime()), 
				new java.sql.Timestamp(employeeLeaves.getEndsAt().getTime()), employeeLeaves.getEmployeeId().toString(), session);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLeaves> loadLeaveReportByEmpId(EmployeeLeaves employeeLeaves, Session session){
		String sqlQuery = "select sum(ld.hours), el.employeeView from EmployeeLeaveDetails as ld left join ld.employeeLeaves as el "
				+ " WHERE  el.active=:active "+ dateBetween+" AND el.requestType in (:requestTypes) AND el.employeeId IN ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ") " 
				+ " group by el.employeeId order by el.startsAt asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("startsAt", employeeLeaves.getStartsAt());
		query.setParameter("endsAt", employeeLeaves.getEndsAt());
//		query.setParameter("employeeId", employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", ""));
		query.setParameterList("requestTypes", getLeaveTypeList(employeeLeaves.getRequestType()));
		List<EmployeeLeaves> empLeaves = query.list();
		return empLeaves;
	}
	 
	private List<String> getLeaveTypeList(String leaveType){
		List<String> leaveTypeList = new ArrayList<>();
		if(leaveType.equals(LeaveType.ALL_PTO.getLeaveType())){
			leaveTypeList.add(LeaveType.PTO.getLeaveType());
			leaveTypeList.add(LeaveType.UNPLANNED_PTO.getLeaveType());
			leaveTypeList.add(LeaveType.COMPENSATORY_OFF.getLeaveType());
			leaveTypeList.add(LeaveType.LOP.getLeaveType());
			leaveTypeList.add(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType());
			leaveTypeList.add(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType());
			leaveTypeList.add(LeaveType.PERSONAL_LEAVE.getLeaveType());
			leaveTypeList.add(LeaveType.SABATICAL.getLeaveType());
		} else
			leaveTypeList.add(LeaveType.valueOf(leaveType).getLeaveType());
		return leaveTypeList;
	}
	
	@Override
	public Long getLeaveBalance(String leaveType, String calYear, String calMonth,
		String employeeId, int totalLeaves, Session session) throws ParseException{
		String sqlQuery = "SELECT SUM(el.hours) from EmployeeLeaves el WHERE el.requestType in (:requestTypes) AND el.active=:active AND el.employeeId=:employeeId"+dateBetween;
		Query query = session.createQuery(sqlQuery);
		if(leaveType.equals(LeaveType.PTO.getLeaveType()))
			leaveType = LeaveType.ALL_PTO.getLeaveType();
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("startsAt", new java.sql.Timestamp(DateUtils.getDate(calYear+startDate).getTime()));
		query.setParameter("endsAt", new java.sql.Timestamp(DateUtils.getDate(calYear+"-"+(Integer.parseInt(calMonth))+"-31").getTime()));
		long returnVal = 0;
		if(query.uniqueResult() != null){
			returnVal = (Long)query.uniqueResult();
		}
		return returnVal;
	}
	
	@Override
	public EmployeeLeaves saveLeave(EmployeeLeaves employeeLeaves, Session session){
		session.save(employeeLeaves);
		return employeeLeaves;
	}
	
	@Override
	public EmployeeLeaveDetails saveLeaveDetails(EmployeeLeaveDetails employeeLeaveDetails, Session session){
		session.save(employeeLeaveDetails);
		return employeeLeaveDetails;
	}

	@Override
	public boolean updateLeave(EmployeeLeaves employeeLeaves, Session session){
		session.merge(employeeLeaves);
		return true;
	}
	
	@Override
	public boolean updateLeaveDetails(Long empLeaveId, boolean isActive, Session session){
		String sqlQuery = "Update EmployeeLeaveDetails el set el.active=:active where el.employeeLeavesId=:employeeLeavesId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", isActive);
		query.setParameter("employeeLeavesId", empLeaveId);
		query.executeUpdate();
		return true;
	}
	
	@Override
	public EmployeeLeaves loadLeaveById(String leaveId, Session session){
		return (EmployeeLeaves)session.get(EmployeeLeaves.class, Long.parseLong(leaveId));
	}
	
	@Override
	public EmployeeLeaveDetails loadLeaveDetailsByDtAndLeaveId(Long empLeaveId, Date date, Session session){
		String sqlQuery = "from EmployeeLeaveDetails el WHERE el.active=:active and el.employeeLeavesId=:employeeLeavesId "
				+ "and el.leaveDate=:leaveDate";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("employeeLeavesId", empLeaveId);
		query.setParameter("leaveDate", date);
		return (EmployeeLeaveDetails) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean carryLeaves(String year, String employeeId, Session session) throws RecordExistsException {
		Employee employee = employeeDAO.loadById(employeeId, session);
		String sqlQuery = "from EmployeeLeaveMaintenance el WHERE el.year=:year";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("year", Integer.parseInt(year));
		if(query.list().size() > 0){
			throw new RecordExistsException();
		} else {
			sqlQuery = "Select el.employeeId, sum(el.hours) from EmployeeLeaves el where el.active=:active "+dateBetween+" group by el.employeeId";
			Query leaveQuery = session.createQuery(sqlQuery);
			leaveQuery.setParameter("active", PerfHrConstants.ACTIVE);
			try {
				leaveQuery.setParameter("startsAt", DateUtils.getDate(year+"-01-01"));
				leaveQuery.setParameter("endsAt", DateUtils.getDate(year+"-12-31"));
			} catch (HibernateException | ParseException e) {
				logger.info("Invalid year parsed!");
			}
			List<Object[]> leaves = leaveQuery.list();
			for(Object[] leave: leaves){
				logger.info("leaves "+leave[0]);
				EmployeeLeaveMaintenance leaveMaintanence = new EmployeeLeaveMaintenance();
				leaveMaintanence.setEmployeeId(Long.parseLong(String.valueOf(leave[0])));
				leaveMaintanence.setYear(Integer.parseInt(year));
				int leaveBal = Integer.parseInt(String.valueOf(leave[1]))/8;
				if(leaveBal <= 20){
					leaveBal = (20 - leaveBal) > 10 ? 10:(20 - leaveBal);
					leaveMaintanence.setCarriedOn(leaveBal);
				} else
					leaveMaintanence.setByeOut(leaveBal-20);
				leaveMaintanence.setCreatedBy(employee.getPk());
				leaveMaintanence.setModifiedBy(employee.getPk());
				leaveMaintanence.setDtCreated(new Date());
				leaveMaintanence.setDtModified(new Date());
				session.save(leaveMaintanence);
			}
		}
		return true;
	}

	@Override
	public EmployeeLeaveMaintenance getCarryLeaves(String employeeId, String year, Session session) {
		String sql = "FROM EmployeeLeaveMaintenance elm WHERE elm.active=:active and elm.employeeId=:employeeId and elm.year=:year";
		Query query = session.createQuery(sql);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameter("year", Integer.parseInt(year));
		return (EmployeeLeaveMaintenance) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLeaves> isLeaveAppliedExactly(EmployeeLeaves employeeLeaves,Session session,String flag) {
		List<EmployeeLeaves>  empLeaves = null;
		String sql = "SELECT * FROM employee_leaves_details where employee_leaves_pk"
				+ " in (SELECT pk FROM employee_leaves e where employee_pk= '"+employeeLeaves.getEmployeeId()+"') and leave_dt between '"+new java.sql.Timestamp(employeeLeaves.getStartsAt().getTime()) +"' and '"+new java.sql.Timestamp(employeeLeaves.getEndsAt().getTime())+"' " ;
		if(flag != null && !"".equalsIgnoreCase(flag) && flag.equalsIgnoreCase("U")){
			sql =  "SELECT * FROM employee_leaves_details where employee_leaves_pk"
						+ " in (SELECT pk FROM employee_leaves e where employee_pk= '"+employeeLeaves.getEmployeeId()+"' and pk <> '"+employeeLeaves.getPk()+"') and leave_dt between '"+new java.sql.Timestamp(employeeLeaves.getStartsAt().getTime())+"' and '"+new java.sql.Timestamp(employeeLeaves.getEndsAt().getTime())+"' "; 
		}
		Query query = session.createSQLQuery(sql);
		empLeaves = query.list();
		return empLeaves;
	}
	
	@SuppressWarnings("unchecked")
	public List<Notification> getNotificationDetails(Session session) {
		String sql = "from Notification where mailStatus =:flag";
		Query query = session.createQuery(sql);
		query.setParameter("flag", PerfHrConstants.PENDING);
		List<Notification> notifications = query.list();
		return notifications;
	}

	@Override
	public Object updateNotification(Notification notification, Session session) {
		session.update(notification);
		return true;
	}

}