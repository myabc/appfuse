<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="${pojoNameLower}Detail.title"/></title>
    <meta name="heading" content="<fmt:message key='${pojoNameLower}Detail.heading'/>"/>
</head>

<form:form commandName="${pojoNameLower}" method="post" action="${pojoNameLower}form.html" id="${pojoNameLower}Form" onsubmit="return validate${pojo.shortName}(this)">
<form:errors path="*" cssClass="error" element="div"/>
<#rt/>
<#foreach field in pojo.getAllPropertiesIterator()>
<#if field.equals(pojo.identifierProperty)>
    <#assign idFieldName = field.name>
    <#if field.value.identifierGeneratorStrategy == "assigned">
        <#lt/><ul>
    <li>
        <appfuse:label styleClass="desc" key="${pojoNameLower}.${field.name}"/>
        <form:errors path="${field.name}" cssClass="fieldError"/>
        <form:input path="${field.name}" id="${field.name}" cssClass="text medium"/>
    </li>
    <#else>
        <#lt/><form:hidden path="${field.name}"/>
        <#lt/><ul>
    </#if>
<#elseif !c2h.isCollection(field) && !c2h.isManyToOne(field)>
    <#foreach column in field.getColumnIterator()>
    <li>
        <appfuse:label styleClass="desc" key="${pojoNameLower}.${field.name}"/>
        <form:errors path="${field.name}" cssClass="fieldError"/>
        <#if field.value.typeName == "java.util.Date">
        <script type="text/javascript">dojo.require("dojo.widget.DatePicker");</script>
        <div dojoType="dropdowndatepicker" name="${field.name}" value="${'$'}{${pojoNameLower}.${field.name}}" class="text medium"
             displayFormat="${'$'}{datePattern}" saveFormat="${'$'}{datePattern}" containerToggle="wipe" containerToggleDuration="300"/>
        <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
        <form:checkbox path="${field.name}" id="${field.name}" cssClass="checkbox"/>
        <#else>
        <form:input path="${field.name}" id="${field.name}" cssClass="text medium"/>
        </#if>
    </li>
    </#foreach>
<#elseif c2h.isManyToOne(field)>
    <#foreach column in field.getColumnIterator()>
            <#lt/>    <!-- todo: change this to read the identifier field from the other pojo -->
            <#lt/>    <s:select name="${pojoNameLower}.${field.name}.id" list="${field.name}List" listKey="id" listValue="id"></s:select>
    </#foreach>
</#if>
</#foreach>

    <li class="buttonBar bottom">
        <input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>
        <c:if test="${'$'}{not empty ${pojoNameLower}.${idFieldName}}">
        <input type="submit" class="button" name="delete" onclick="bCancel=true;return confirmDelete('${pojoNameLower}')"
            value="<fmt:message key="button.delete"/>" />
        </c:if>
        <input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>" onclick="bCancel=true"/>
    </li>
</ul>
</form:form>

<v:javascript formName="${pojoNameLower}" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

<script type="text/javascript">
    Form.focusFirstElement($('${pojoNameLower}Form'));
</script>