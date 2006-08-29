<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="signup.title"/></title>
<content tag="heading"><fmt:message key="signup.heading"/></content>
<body id="signup"/>

<div class="separator"></div>

<ww:form name="signupForm" action="signup" method="post" validate="true">
    <li class="info">
        <fmt:message key="signup.message"/>
    </li>
    <ww:textfield label="%{getText('user.username')}" name="user.username"
        value="%{user.username}" cssClass="text large" required="true"/>

    <li>
        <div>
            <div class="left">
                <ww:password label="%{getText('user.password')}" name="user.password" show="true" theme="xhtml" value="%{user.password}"
                    required="true" cssClass="text medium"/>
            </div>
            <div>
                <ww:password label="%{getText('user.confirmPassword')}" name="user.confirmPassword" theme="xhtml" value="%{user.confirmPassword}"
                    required="true" show="true" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <ww:textfield label="%{getText('user.passwordHint')}" name="user.passwordHint"
        value="%{user.passwordHint}" required="true" cssClass="text large"/>

    <li>
        <div>
            <div class="left">
                <ww:textfield label="%{getText('user.firstName')}" name="user.firstName" theme="xhtml"
                    value="%{user.firstName}" required="true" cssClass="text medium"/>
            </div>
            <div>
                <ww:textfield label="%{getText('user.lastName')}" name="user.lastName" theme="xhtml"
                    value="%{user.lastName}" required="true" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <li>
        <div>
            <div class="left">
                <ww:textfield label="%{getText('user.email')}" name="user.email" theme="xhtml"
                    value="%{user.email}" required="true" cssClass="text medium"/>
            </div>
            <div>
                <ww:textfield label="%{getText('user.phoneNumber')}" name="user.phoneNumber" theme="xhtml"
                    value="%{user.phoneNumber}" cssClass="text medium"/>
            </div>
        </div>
    </li>

    <ww:textfield label="%{getText('user.website')}" name="user.website"
        value="%{user.website}" required="true" cssClass="text large"/>

    <li>
        <label class="desc"><fmt:message key="user.address.address"/></label>
        <div class="group">
            <div>
                <ww:textfield label="%{getText('user.address.address')}" name="user.address.address"
                    value="%{user.address.address}" cssClass="text large" labelposition="bottom"/>
            </div>
            <div class="left">
                <ww:textfield label="%{getText('user.address.city')}" name="user.address.city"
                    value="%{user.address.city}" required="true" cssClass="text medium" labelposition="bottom"/>
            </div>
            <div>
                <ww:textfield label="%{getText('user.address.province')}" name="user.address.province"
                    value="%{user.address.province}" required="true" cssClass="text state" size="2" labelposition="bottom"/>
            </div>
            <div class="left">
                <ww:textfield label="%{getText('user.address.postalCode')}" name="user.address.postalCode"
                    value="%{user.address.postalCode}" required="true" cssClass="text zip" labelposition="bottom"/>
            </div>
            <div>
                <ww:set name="country" value="user.address.country" scope="page"/>
                <appfuse:country name="user.address.country" prompt="" default="${country}"/>
                <p><label for="user.address.country"><fmt:message key="user.address.country"/> <span class="req">*</span></p>
            </div>
        </div>
    </li>
    <li class="buttonBar bottom">
        <input type="submit" class="button" name="save" value="<fmt:message key="button.register"/>"/>
        <input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>
    </li>
</ww:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["signupForm"]);
</script>
