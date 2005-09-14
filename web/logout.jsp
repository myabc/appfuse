<%@ include file="/common/taglibs.jsp"%>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="net.sf.acegisecurity.ui.rememberme.TokenBasedRememberMeServices" %>

<%
session.invalidate();
Cookie terminate = new Cookie(TokenBasedRememberMeServices.ACEGI_SECURITY_HASHED_REMEMBER_ME_COOKIE_KEY, null);

terminate.setMaxAge(0);
response.addCookie(terminate);
%>

<c:redirect url="/mainMenu.html"/>
