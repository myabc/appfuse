<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<!--${pojo.shortName}Action-START-->
        <action name="${util.getPluralForWord(pojoNameLower)}" class="${basepackage}.webapp.action.${pojo.shortName}Action" method="list">
            <result>/WEB-INF/pages/${pojoNameLower}List.jsp</result>
        </action>

        <action name="edit${pojo.shortName}" class="${basepackage}.webapp.action.${pojo.shortName}Action" method="edit">
            <result>/WEB-INF/pages/${pojoNameLower}Form.jsp</result>
            <result name="error">/WEB-INF/pages/${pojoNameLower}List.jsp</result>
        </action>

        <action name="save${pojo.shortName}" class="${basepackage}.webapp.action.${pojo.shortName}Action" method="save">
            <result name="input">/WEB-INF/pages/${pojoNameLower}Form.jsp</result>
            <result name="cancel" type="redirect">${util.getPluralForWord(pojoNameLower)}.html</result>
            <result name="delete" type="redirect">${util.getPluralForWord(pojoNameLower)}.html</result>
            <result name="success" type="redirect">${util.getPluralForWord(pojoNameLower)}.html</result>
        </action>
        <!--${pojo.shortName}Action-END-->

        <!-- Add additional actions here -->