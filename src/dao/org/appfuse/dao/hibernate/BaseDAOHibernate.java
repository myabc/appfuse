package org.appfuse.dao.hibernate;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.appfuse.dao.DAO;
import org.springframework.orm.ObjectRetrievalFailureException;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use.</p>
 *
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {
    protected transient final Log log = LogFactory.getLog(getClass());

    /**
     * @see org.appfuse.dao.DAO#getObject(java.lang.Object)
     */
    public Object getObject(Object o) {
        Long id = null;

        // use reflection to get the id and fetch the object
        try {
            Method meth = o.getClass().getMethod("getId", new Class[0]);
            id = (Long) meth.invoke(o, new Object[0]);
            o = getHibernateTemplate().get(o.getClass(), id);
        } catch (Exception e) {
            throw new ObjectRetrievalFailureException(o.getClass(), id);
        }

        return o;
    }

    /**
     * @see org.appfuse.dao.DAO#saveObject(java.lang.Object)
     */
    public Object saveObject(Object o) {
        getHibernateTemplate().saveOrUpdateCopy(o);

        return o;
    }

    /**
     * @see org.appfuse.dao.DAO#removeObject(java.lang.Object)
     */
    public void removeObject(Object o) {
        if (log.isDebugEnabled()) {
            log.debug("loading object to delete....");
        }

        o = getObject(o);

        if (o != null) {
            getHibernateTemplate().delete(o);
        }
    }
}
