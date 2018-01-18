package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeRolesDAO;
import com.perficient.hr.dao.RolesDAO;
import com.perficient.hr.model.EmployeeRoles;
import com.perficient.hr.model.Roles;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeRolesDAO")
public class EmployeeRolesDAOImpl implements EmployeeRolesDAO {

	protected Logger logger = LoggerFactory.getLogger(EmployeeRolesDAOImpl.class);
	
	@Autowired
    RolesDAO rolesDAO;
	
	@Override
	public EmployeeRoles saveEmpRoles(EmployeeRoles employeeRoles, Session session) {
		session.evict(employeeRoles);
		/*EmployeeRoles employeeRoles2 = getEmpRolesByRoleandEmployeeId(employeeRoles, session);
		if(getEmpRolesByRoleandEmployeeId(employeeRoles, session) == null)
		{*/
			employeeRoles.setActive(PerfHrConstants.ACTIVE);
			session.merge(employeeRoles);
//		}
		return employeeRoles;
	}
	
	public EmployeeRoles getEmpRolesByRoleandEmployeeId(EmployeeRoles employeeRoles, Session session){
		Criteria criteria =  session.createCriteria(EmployeeRoles.class);
		criteria.add(Restrictions.eq("employee.pk", employeeRoles.getEmployee().getPk()));
		criteria.add(Restrictions.eq("roleId.pk", employeeRoles.getRoleId().getPk()));
		criteria.add(Restrictions.eq(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE));
		EmployeeRoles employeeRoles2 = null;
		if(criteria.list() != null && criteria.list().size() > 0)
		{
			employeeRoles2 = (EmployeeRoles) criteria.list().get(0);
		}
		return employeeRoles2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeRoles> loadEmpRolesByRoles(String roleId, Session session) {
		String sqlQuery =" from EmployeeRoles r where r.active=:active and r.roleId=:roleId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("roleId", rolesDAO.loadRolesById(roleId, session));
		return query.list();
	}

	@Override
	public int removeEmpRoles(Roles role, Session session) {
		String sqlQuery =" UPDATE EmployeeRoles r set r.active=:active where r.roleId=:roleId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.INACTIVE);
		query.setParameter("roleId", rolesDAO.loadRolesById(String.valueOf(role.getPk()), session));
		return query.executeUpdate();
	}
	
	@Override
	public int removeEmpRoles(Long roldId, String empIds, Session session) {
		String sqlQuery = "Update perficient.employee_roles er set er.active = " + PerfHrConstants.INACTIVE
				+ " , er.dt_modified = current_timestamp() where er.role_pk = " + roldId + " and "
				+ " employee_pk NOT IN (" + empIds + ") and active = " + PerfHrConstants.ACTIVE;
		Query query = session.createSQLQuery(sqlQuery);
		return query.executeUpdate();
	}
	
	@Override
	public int removeEmpRolesByEmpIds(Roles role, Integer empId, Session session) {
		String sqlQuery =" UPDATE EmployeeRoles r set r.active=:active "
				+ "where r.roleId=:roleId and r.employeePk = :empIds and r.active = :activeCondition";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.INACTIVE);
		query.setParameter("activeCondition", PerfHrConstants.ACTIVE);
		query.setParameter("empIds", empId);
		query.setParameter("roleId", rolesDAO.loadRolesById(String.valueOf(role.getPk()), session));
		return query.executeUpdate();
	}
	
}
