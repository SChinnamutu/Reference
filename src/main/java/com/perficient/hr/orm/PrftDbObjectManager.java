package com.perficient.hr.orm;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("prftDbObj")
public abstract class PrftDbObjectManager<T> {

	public PrftDbObjectManager(){
		
	}
	
	protected final Logger logger = LoggerFactory.getLogger(PrftDbObjectManager.class);
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
	
	public boolean exists(T object, String idKey, Object idValue, Long pk) throws RecordExistsException {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(object.getClass());
		criteria.add(Restrictions.eq(idKey, idValue)).add(Restrictions.eq(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE));
		if(null != pk)
			criteria.add(Restrictions.not(Restrictions.in("pk", new Long[] {pk})));
		criteria.setProjection(Projections.property(idKey));
		if(criteria.list().size() > 0)
				throw new RecordExistsException();
		return false;
	}
	
	public boolean exists(Class<? extends Object> clazz, String idKey, Object idValue, Long pk) throws RecordExistsException {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq(idKey, idValue)).add(Restrictions.eq(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE));
		if(null != pk)
			criteria.add(Restrictions.not(Restrictions.in("pk", new Long[] {pk})));
		criteria.setProjection(Projections.property(idKey));
		if(criteria.list().size() > 0)
				throw new RecordExistsException();
		return false;
	}
	
	public boolean exists(T object, HashMap<Object, Object> mapCriteria, Long pk) throws RecordExistsException {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(object.getClass());
		for(Object key : mapCriteria.keySet()){
			criteria.add(Restrictions.eq(key.toString(), mapCriteria.get(key)));	
		}
		if(null != pk)
			criteria.add(Restrictions.not(Restrictions.in("pk", new Long[] {pk})));
		//criteria.setProjection(Projections.property(idKey));
		if(criteria.list().size() > 0)
				throw new RecordExistsException();
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public List executeQuery(String sqlQuery, HashMap<Object, Object> mapCriteria){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(sqlQuery);
		for(Object key : mapCriteria.keySet()){
			query.setParameter(key.toString(), mapCriteria.get(key));
		}
		return  query.list();
	}
	
}
