package org.appfuse.webapp.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;
import org.appfuse.Constants;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import java.util.HashMap;

/**
 * Base class for running Struts 2 Action tests.
 * @author mraible
 */
public abstract class BaseActionTestCase extends AbstractTransactionalDataSourceSpringContextTests {
    protected transient final Log log = logger;

    protected String[] getConfigLocations() {
        super.setAutowireMode(AUTOWIRE_BY_NAME);
        return new String[] {
                "classpath*:/applicationContext-dao.xml",
                "classpath*:/applicationContext-service.xml",
                "/WEB-INF/applicationContext*.xml"
            };
    }

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        LocalizedTextUtil.addDefaultResourceBundle(Constants.BUNDLE_KEY); 
        ActionContext.getContext().setSession(new HashMap());
        
        // change the port on the mailSender so it doesn't conflict with an 
        // existing SMTP server on localhost
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) applicationContext.getBean("mailSender");
        mailSender.setPort(2525);
        mailSender.setHost("localhost");

        // populate the request so getRequest().getSession() doesn't fail in BaseAction.java
        ServletActionContext.setRequest(new MockHttpServletRequest());
    }

    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        ActionContext.getContext().setSession(null);   
    }
}
