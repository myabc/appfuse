package org.appfuse.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.event.PageRenderListener;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidationConstraint;
import org.appfuse.Constants;
import org.appfuse.model.Role;
import org.appfuse.model.User;
import org.appfuse.service.MailEngine;
import org.appfuse.service.RoleManager;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.appfuse.util.StringUtil;
import org.appfuse.webapp.util.RequestUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;

public abstract class UserForm extends BasePage implements PageRenderListener {
    private IPropertySelectionModel countries;
    private IPropertySelectionModel availableRoles;
    public abstract UserManager getUserManager();
    public abstract void setUserManager(UserManager mgr);
    public abstract RoleManager getRoleManager();
    public abstract void setRoleManager(RoleManager mgr);
    public abstract void setUser(User user);
    public abstract User getUser();
    public abstract void setFrom(String from);
    public abstract String getFrom();

    public IPropertySelectionModel getAvailableRoles() {
        if (availableRoles == null) {
            List roles =
                (List) getServletContext().getAttribute(Constants.AVAILABLE_ROLES);
            availableRoles = new RoleModel(roles);
        }

        return availableRoles;
    }

    public IPropertySelectionModel getCountries() {
        if (countries == null) {
            countries = new CountryModel(getLocale());
        }

        return countries;
    }

    public void pageBeginRender(PageEvent event) {
        if ((getUser() == null) && !event.getRequestCycle().isRewinding()) {
            setUser(new User());
            getUser().addRole(new Role(Constants.USER_ROLE));
        } else if (event.getRequestCycle().isRewinding()) {
            setUser(new User());
            getUser().addRole(new Role(Constants.USER_ROLE));
        }

        // if user logged in with a cookie, display a warning that they
        // can't change passwords
        if (log.isDebugEnabled()) {
            log.debug("checking for cookieLogin...");
        }

        if (getSession().getAttribute("cookieLogin") != null) {
            setMessage(getMessage("userProfile.cookieLogin"));
        }
    }

    public void cancel(IRequestCycle cycle) {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'cancel' method");
        }

        if (!StringUtils.equals(getRequest().getParameter("from"), "Slist")) {
            cycle.activate("mainMenu");
        } else {
            cycle.activate("users");
        }
    }

    public void save(IRequestCycle cycle) throws UserExistsException {
        if (log.isDebugEnabled()) {
            log.debug("entered save method");
        }

        // make sure the password fields match
        IValidationDelegate delegate = getValidationDelegate();

        if (!StringUtils.equals(getUser().getPassword(),
                                    getUser().getConfirmPassword())) {
            addError(delegate, "confirmPasswordField",
                     format("errors.twofields",
                            getMessage("user.confirmPassword"),
                            getMessage("user.password")),
                     ValidationConstraint.CONSISTENCY);
        }

        if (delegate.getHasErrors()) {
            return;
        }

        String password = getUser().getPassword();
        String originalPassword = getRequest().getParameter("originalPassword");
        
        if (StringUtils.equals(getRequest().getParameter("encryptPass"), "true") ||
                !StringUtils.equals("S"+password, originalPassword)) {
            String algorithm =
                (String) getConfiguration().get(Constants.ENC_ALGORITHM);

            if (algorithm == null) { // should only happen for test case
                log.debug("assuming testcase, setting algorigthm to 'SHA'");
                algorithm = "SHA";
            }

            getUser().setPassword(StringUtil.encodePassword(password, algorithm));
        }

        // workaround for input tags that don't aren't set by Tapestry (who knows why)
        boolean fromList =
            StringUtils.equals(getRequest().getParameter("from"), "Slist");
        String[] userRoles = null;

        if (fromList) {
            userRoles = getRequest().getParameterValues("userRoles");
        } else {
            userRoles = getRequest().getParameterValues("hiddenUserRoles");
        }

        User user = getUser();
        UserManager userManager = getUserManager();

        user.getRoles().clear();
        for (int i = 0; (userRoles != null) && (i < userRoles.length); i++) {
            String roleName = userRoles[i];
            user.addRole(getRoleManager().getRole(roleName));
        }

        try {
            userManager.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            log.warn(e.getMessage());
            addError(delegate, "emailField",
                     format("errors.existing.user", user.getUsername(),
                            user.getEmail()), ValidationConstraint.CONSISTENCY);
            return;
        }

        HttpSession session = getSession();
        HttpServletRequest request = getRequest();

        if (!fromList && user.getUsername().equals(getRequest().getRemoteUser())) {
            session.setAttribute(Constants.USER_KEY, user);

            // update the user's remember me cookie if they didn't login
            // with a cookie
            if ((RequestUtil.getCookie(request, Constants.LOGIN_COOKIE) != null) &&
                    (session.getAttribute("cookieLogin") == null)) {
                // delete all user cookies and add a new one
                userManager.removeLoginCookies(user.getUsername());

                String autoLogin =
                    userManager.createLoginCookie(user.getUsername());
                RequestUtil.setCookie(getResponse(), Constants.LOGIN_COOKIE,
                                      autoLogin, request.getContextPath());
            }

            // add success messages
            MainMenu nextPage = (MainMenu) cycle.getPage("mainMenu");
            nextPage.setMessage(format("user.saved", user.getFullName()));
            cycle.activate(nextPage);
        } else {
            // add success messages
            if ("X".equals(request.getParameter(("version")))) {                
                sendNewUserEmail(request, user);
                UserList nextPage = (UserList) cycle.getPage("users");
                nextPage.setMessage(format("user.added", user.getFullName()));
                cycle.activate(nextPage); // return to the list screen
            } else {
                setMessage(format("user.updated.byAdmin", user.getFullName()));
                cycle.activate("userForm"); // return to current page
            }
        }
    }

    public void delete(IRequestCycle cycle) {
        if (log.isDebugEnabled()) {
            log.debug("entered delete method");
        }

        getUserManager().removeUser(getUser().getUsername());

        UserList nextPage = null;
        if ("test".equals(getFrom())) {
            nextPage = (UserList) cycle.getPage("userList");
        } else {
            nextPage = (UserList) cycle.getPage("users");
        }

        nextPage.setMessage(format("user.deleted", getUser().getFullName()));
        cycle.activate(nextPage);
    }

    // Form Controls ==========================================================
    public List getUserRoles() {
        List selectedRoles = new ArrayList(getUser().getRoles().size());

        for (Iterator it = getUser().getRoles().iterator();
                 (it != null) && it.hasNext();) {
            Role role = (Role) it.next();
            selectedRoles.add(role.getName());
        }

        return selectedRoles;
    }

    private void sendNewUserEmail(HttpServletRequest request, User user) {
        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + user.getUsername() +
                      "' an account information e-mail");
        }

        Map global = (Map) getGlobal();
        ApplicationContext ctx =
            (ApplicationContext) global.get(BaseEngine.APPLICATION_CONTEXT_KEY);

        SimpleMailMessage message =
            (SimpleMailMessage) ctx.getBean("mailMessage");
        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");

        StringBuffer msg = new StringBuffer();
        msg.append(format("newuser.email.message", user.getFullName()));
        msg.append("\n\n" + getMessage("user.username"));
        msg.append(": " + user.getUsername() + "\n");
        msg.append(getMessage("user.password") + ": ");
        msg.append(user.getPassword());
        msg.append("\n\nLogin at: " + RequestUtil.getAppURL(request));
        message.setText(msg.toString());

        message.setSubject(getMessage("signup.email.subject"));

        MailEngine engine = (MailEngine) ctx.getBean("mailEngine");
        engine.send(message);
    }
}
