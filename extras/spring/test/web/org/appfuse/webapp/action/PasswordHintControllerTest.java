package org.appfuse.webapp.action;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.dumbster.smtp.SimpleSmtpServer;


public class PasswordHintControllerTest extends BaseControllerTestCase {
    private PasswordHintController c = null;
    
    public void setPasswordHintController(PasswordHintController password) {
        this.c = password;
    }

    public void testExecute() throws Exception {
        MockHttpServletRequest request = newGet("/passwordHint.html");
        request.addParameter("username", "tomcat");

        SimpleSmtpServer server = SimpleSmtpServer.start(2525);
        
        c.handleRequest(request, new MockHttpServletResponse());
        
        // verify an account information e-mail was sent
        server.stop();
        assertTrue(server.getReceivedEmailSize() == 1);
        
        // verify that success messages are in the session
        assertNotNull(request.getSession().getAttribute("messages"));
    }
}
