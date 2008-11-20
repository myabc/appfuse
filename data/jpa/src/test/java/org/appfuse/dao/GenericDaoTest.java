package org.appfuse.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.dao.jpa.GenericDaoJpa;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

public class GenericDaoTest extends BaseDaoTestCase {
    Log log = LogFactory.getLog(GenericDaoTest.class);
    GenericDao<User, Long> genericDao;
    @PersistenceContext(unitName=GenericDaoJpa.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Before
    public void setUp() {
        genericDao = new GenericDaoJpa<User, Long>(User.class, entityManager);
    }

    @Test
    public void getUser() {
        User user = genericDao.get(-1L);
        assertNotNull(user);
        assertEquals("user", user.getUsername());
    }
}
