package org.appfuse.webapp.action;

import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.Constants;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class UserFormControllerTest extends BaseControllerTestCase {
    private static Log log = LogFactory.getLog(UserFormControllerTest.class);
    private UserFormController c;
    private MockHttpServletRequest request;
    private ModelAndView mv;

    protected void setUp() throws Exception {
        // needed to initialize a user
        super.setUp();
        c = (UserFormController) ctx.getBean("userFormController");
    }

    protected void tearDown() {
        c = null;
    }

    public void testCancel() throws Exception {
        log.debug("testing cancel...");
        request = newPost("/editUser.html");
        request.getSession().setAttribute(Constants.USER_KEY, user);
        request.addParameter("cancel", "");

        mv = c.handleRequest(request, new MockHttpServletResponse());

        assertEquals("mainMenu.html", ((RedirectView) mv.getView()).getUrl());
    }

    public void testEdit() throws Exception {
        log.debug("testing edit...");
        request = newGet("/editUser.html");
        request.addParameter("username", "tomcat");

        mv = c.handleRequest(request, new MockHttpServletResponse());

        assertEquals("userForm", mv.getViewName());
    }

    public void testSave() throws Exception {
        request = newPost("/editUser.html");
        super.objectToRequestParameters(user, request);
        request.addParameter("confirmPassword", user.getPassword());
        request.addParameter("lastName", "Updated Last Name");
        request.getSession().setAttribute(Constants.USER_KEY, user);
        mv = c.handleRequest(request, new MockHttpServletResponse());
        Errors errors =
            (Errors) mv.getModel().get(BindException.ERROR_KEY_PREFIX + "user");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("messages"));
    }
    
    public void testAddWithMissingFields() throws Exception {
        request = newPost("/editUser.html");
        // an empty id parameter is the trigger for a new user
        request.addParameter("id", "");
        request.addParameter("firstName", "Julie");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        Errors errors =
            (Errors) mv.getModel().get(BindException.ERROR_KEY_PREFIX + "user");
        assertTrue(errors.getAllErrors().size() == 10);
    }
    
    public void testRemove() throws Exception {
        // get the user first so their information is in the request
        // TODO: Fix UserFormController so validation is off for delete
        //request = newGet("/editUser.html");
        //request.addParameter("username", "mraible");

        //mv = c.handleRequest(request, new MockHttpServletResponse());
        
        request = newPost("/editUser.html");
        request.addParameter("delete", "");
        request.addParameter("username", "mraible");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertNotNull(request.getSession().getAttribute("messages"));
    }
}
