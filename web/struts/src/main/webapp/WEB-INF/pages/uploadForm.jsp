<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="upload.title"/></title>
    <content tag="heading"><fmt:message key="upload.heading"/></content>
    <meta name="menu" content="FileUpload"/>
</head>

<s:form action="uploadFile" enctype="multipart/form-data" method="post" validate="true" id="uploadForm">
    <li class="info">
        <fmt:message key="upload.message"/>
    </li>
    <s:textfield name="name" label="%{getText('uploadForm.name')}" cssClass="text medium" required="true"/>
    <s:file name="file" label="%{getText('uploadForm.file')}" cssClass="text file" required="true"/>
    <li class="buttonBar bottom">
        <s:submit key="button.upload" name="upload" cssClass="button"/>
        <input type="button" value="<fmt:message key="button.cancel"/>" class="button"
            onclick="this.form.onsubmit = null; location.href='mainMenu.html'"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement($('uploadForm'));
</script>

