<%-- Error Messages --%>
<logic:messagesPresent>
    <div class="error">	
        <html:messages id="error">
            <html:img pageKey="icon.warning.img" 
                altKey="icon.warning" styleClass="icon"/>
            <bean:write name="error" filter="false"/><br/>
        </html:messages>
    </div>
</logic:messagesPresent>

<%-- Success Messages --%>
<logic:messagesPresent message="true">
    <div class="message">	
        <html:messages id="message" message="true">
            <html:img pageKey="icon.information.img" 
                altKey="icon.information" styleClass="icon"/>
            <bean:write name="message" filter="false"/><br/>
        </html:messages>
    </div>
</logic:messagesPresent>