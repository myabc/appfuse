<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="display.title"/></title>
<content tag="heading"><fmt:message key="display.heading"/></content>

<p>Below is a list of attributes that were gathered in UploadAction.java.</p>

<div class="separator"></div>

<table class="detail" cellpadding="5">
    <tr>
        <th>Friendly Name:</th>
        <td><ww:property value="name"/></td>
    </tr>
    <tr>
        <th>Filename:</th>
        <td><ww:property value="fileFileName"/></td>
    </tr>
    <tr>
        <th>File content type:</th>
        <td><ww:property value="fileContentType"/></td>
    </tr>
    <tr>
        <th>File size:</th>
        <td><ww:property value="file.length()"/> bytes</td>
    </tr>
    <tr>
        <th class="tallCell">File Location:</th>
        <td>The file has been written to: <br />
            <a href="<c:out value="${link}"/>">
            <c:out value="${location}" escapeXml="false"/></a>
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="buttonBar">
            <input class="button" type="button" value="Done"
                onclick="location.href='mainMenu.html'" />
            <input class="button" type="button" style="width: 120px" value="Upload Another"
                onclick="location.href='uploadFile!default.html'" />
        </td>
    </tr>
</table>



