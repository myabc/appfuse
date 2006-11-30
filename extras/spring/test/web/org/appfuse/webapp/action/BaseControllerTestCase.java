package org.appfuse.webapp.action;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.Constants;
import org.appfuse.util.DateUtil;
import org.appfuse.model.BaseObject;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.web.context.support.XmlWebApplicationContext;

public abstract class BaseControllerTestCase extends AbstractTransactionalDataSourceSpringContextTests {
    protected transient final Log log = LogFactory.getLog(getClass());
    protected User user;

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        String pkg = ClassUtils.classPackageAsResourcePath(Constants.class);
        String[] paths = {
                "classpath*:/" + pkg + "/dao/applicationContext-*.xml",
                "classpath*:META-INF/applicationContext-*.xml",
                "/WEB-INF/applicationContext-validation.xml",
                "/WEB-INF/action-servlet.xml"
            };
        return paths;
    }

    protected void onSetUpBeforeTransaction() throws Exception {
        // populate the userForm and place into session
        UserManager userMgr = (UserManager) applicationContext.getBean("userManager");
        user = (User) userMgr.getUserByUsername("tomcat");

        // change the port on the mailSender so it doesn't conflict with an
        // existing SMTP server on localhost
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) applicationContext.getBean("mailSender");
        mailSender.setPort(2525);
        mailSender.setHost("localhost");
    }

    /**
     * Subclasses can invoke this to get a context key for the given location.
     * This doesn't affect the applicationContext instance variable in this class.
     * Dependency Injection cannot be applied from such contexts.
     */
    protected ConfigurableApplicationContext loadContextLocations(String[] locations) {
        if (logger.isInfoEnabled()) {
            logger.info("Loading additional configuration from: " + StringUtils.arrayToCommaDelimitedString(locations));
        }
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(locations);
        ctx.setServletContext(new MockServletContext());
        ctx.refresh();
        return ctx;
    }
    
    /**
     * Convenience methods to make tests simpler
     */
    public MockHttpServletRequest newPost(String url) {
        return new MockHttpServletRequest("POST", url);
    }

    public MockHttpServletRequest newGet(String url) {
        return new MockHttpServletRequest("GET", url);
    }

    public void objectToRequestParameters(Object o, MockHttpServletRequest request) throws Exception {
        objectToRequestParameters(o, request, null);
    }

    public void objectToRequestParameters(Object o, MockHttpServletRequest request, String prefix) throws Exception {
        Class clazz = o.getClass();
        Field[] fields = getDeclaredFields(clazz);
        AccessibleObject.setAccessible(fields, true);

        for (int i = 0; i < fields.length; i++) {
            Object field = (fields[i].get(o));
            if (field != null) {
                if (field instanceof BaseObject) {
                    // Fix for http://issues.appfuse.org/browse/APF-429
                    if (prefix != null) {
                        objectToRequestParameters(field, request, prefix + "." + fields[i].getName());
                    } else {
                        objectToRequestParameters(field, request, fields[i].getName());
                    }
                } else if (!(field instanceof List) && !(field instanceof Set)) {
                    String paramName = fields[i].getName();
    
                    if (prefix != null) {
                        paramName = prefix + "." + paramName;
                    }
    
                    String paramValue = String.valueOf(fields[i].get(o));
    
                    // handle Dates
                    if (field instanceof java.util.Date) {
                        paramValue = DateUtil.convertDateToString((Date)fields[i].get(o));
                        if ("null".equals(paramValue)) paramValue = "";
                    }
    
                    request.addParameter(paramName, paramValue);
                }
            }
        }
    }
    
    private Field[] getDeclaredFields(Class clazz) {
        Field[] f = new Field[0];
        Class superClazz = clazz.getSuperclass();
        Collection rval = new ArrayList();
        
        if (superClazz != null) {
            rval.addAll(Arrays.asList(getDeclaredFields(superClazz)));
        }
        
        rval.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return (Field[]) rval.toArray(f);
    }
}
