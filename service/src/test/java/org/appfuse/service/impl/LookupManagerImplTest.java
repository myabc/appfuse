package org.appfuse.service.impl;

import org.appfuse.dao.LookupDao;
import org.appfuse.model.Role;
import org.appfuse.model.LabelValue;
import org.jmock.Mock;

import java.util.ArrayList;
import java.util.List;


public class LookupManagerImplTest extends BaseManagerTestCase {
    private LookupManagerImpl mgr = new LookupManagerImpl();
    private Mock lookupDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        lookupDao = new Mock(LookupDao.class);
        mgr.setLookupDao((LookupDao) lookupDao.proxy());
    }

    public void testGetAllRoles() {
        if (log.isDebugEnabled()) {
            log.debug("entered 'testGetAllRoles' method");
        }

        // set expected behavior on dao
        Role role = new Role("admin");
        List<Role> testData = new ArrayList<Role>();
        testData.add(role);
        lookupDao.expects(once()).method("getRoles")
                 .withNoArguments().will(returnValue(testData));

        List<LabelValue> roles = mgr.getAllRoles();
        assertTrue(roles.size() > 0);
        // verify expectations
        lookupDao.verify();
    }
}
