package org.appfuse.webapp.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter to remove messages form the session and put them in the request
 * - to solve the redirect after post issue.
 * 
 * <p><a href="MessageFilter.java.html"><i>View Source</i></a></p>
 *
 * @author  Matt Raible
 */
public class MessageFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
    throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        // grab messages from the session and put them into request
        // this is so they're not lost in a redirect
        Object messages = request.getSession().getAttribute("messages");

        if (messages != null) {
            request.setAttribute("messages", messages);
            request.getSession().removeAttribute("messages");
        } else {
            // workaround for issue with Jetty 6.1.5 (Maven Plugin) and MyFaces 1.2.0
            // http://issues.appfuse.org/browse/APF-856
            request.setAttribute("messages", new ArrayList());
        }
        
        // grab errors from the session and put them into request
        // this is so they're not lost in a redirect
        Object errors = request.getSession().getAttribute("errors");

        if (errors != null) {
            request.setAttribute("errors", errors);
            request.getSession().removeAttribute("errors");
        } else {
            // workaround for issue with Jetty 6.1.5 (Maven Plugin) and MyFaces 1.2.0
            // http://issues.appfuse.org/browse/APF-856
            request.setAttribute("errors", new ArrayList());
        }

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
