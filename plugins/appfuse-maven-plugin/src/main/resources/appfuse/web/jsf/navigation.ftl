<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<!--${pojo.shortName}-nav-START-->
    <navigation-rule>
        <from-view-id>/${pojoNameLower}s.xhtml</from-view-id>
        <navigation-case>
            <from-outcome>add</from-outcome>
            <to-view-id>/${pojoNameLower}Form.xhtml</to-view-id>
        </navigation-case>
        <navigation-case>
            <from-outcome>edit</from-outcome>
            <to-view-id>/${pojoNameLower}Form.xhtml</to-view-id>
        </navigation-case>
    </navigation-rule>
    <navigation-rule>
        <from-view-id>/${pojoNameLower}Form.xhtml</from-view-id>
        <navigation-case>
            <from-outcome>cancel</from-outcome>
            <to-view-id>/${pojoNameLower}s.xhtml</to-view-id>
            <redirect/>
        </navigation-case>
        <navigation-case>
            <from-outcome>list</from-outcome>
            <to-view-id>/${pojoNameLower}s.xhtml</to-view-id>
            <redirect/>
        </navigation-case>
    </navigation-rule>
    <!--${pojo.shortName}-nav-END-->
    <!-- Add additional rules here -->