<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="flushCache.title"/></title>
    <content tag="heading"><fmt:message key="flushCache.heading"/></content>
    <meta name="menu" content="AdminMenu"/>
</head>

<cache:flush/>
<div class="message" style="font-size: 12px">
    <p><img src="<c:url value="/images/iconInformation.gif"/>"
        alt="<fmt:message key="icon.information"/>" class="icon" />
    <fmt:message key="flushCache.message"/></p>
</div>
<script type="text/javascript">
    window.setTimeout("history.back()", 2000);
</script>