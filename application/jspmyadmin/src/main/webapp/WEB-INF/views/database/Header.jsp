<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<m:open />
<m:store name="lbl_tables" key="lbl.tables" />
<li ng-show="menuIndex === 2" ng-class="{true:'active', false:''}[menuActiveIndex === 1]">
    <a href="" ng-click="goToState('database_tables', 2)">
        <img alt="${lbl_tables}" class="icon" src="${pageContext.request.contextPath}/components/icons/database-g.png"> ${lbl_tables}
	</a>
</li>
<m:store name="lbl_views" key="lbl.views" />
<li ng-show="menuIndex === 2" ng-class="{true:'active', false:''}[menuActiveIndex === 2]">
    <a href="" ng-click="goToState('database_views', 2)">
        <img alt="${lbl_views}" class="icon" src="${pageContext.request.contextPath}/components/icons/newspaper.png">
		${lbl_views}
	</a>
</li>
<m:store name="lbl_procedures" key="lbl.procedures" />
<li ng-show="menuIndex === 2" ng-class="{true:'active', false:''}[menuActiveIndex === 3]">
    <a  href="" ng-click="goToState('database_procedures', 2)">
        <img alt="${lbl_procedures}" class="icon" src="${pageContext.request.contextPath}/components/icons/historical.png">
    	${lbl_procedures}
	</a>
</li>
<m:store name="lbl_functions" key="lbl.functions" />
<li ng-show="menuIndex === 2" ng-class="{true:'active', false:''}[menuActiveIndex === 4]">
    <a  href="" ng-click="goToState('database_functions', 2)">
        <img alt="${lbl_functions}" class="icon" src="${pageContext.request.contextPath}/components/icons/historical.png">
	    ${lbl_functions}
	</a>
</li>
<m:store name="lbl_events" key="lbl.events" />
<li ng-show="menuIndex === 2" ng-class="{true:'active', false:''}[menuActiveIndex === 5]">
    <a href="" ng-click="goToState('database_events', 2)">
        <img alt="${lbl_events}" class="icon" src="${pageContext.request.contextPath}/components/icons/time.png">
		${lbl_events}
	</a>
</li>
<m:store name="lbl_triggers" key="lbl.triggers" />
<li ng-show="menuIndex === 2" ng-class="{true:'active', false:''}[menuActiveIndex === 6]">
    <a href="" ng-click="goToState('database_triggers', 2)">
        <img alt="${lbl_triggers}" class="icon" src="${pageContext.request.contextPath}/components/icons/index.png">
		${lbl_triggers}
	</a>
</li>
<m:store name="lbl_query_editor" key="lbl.query_editor" />
<li ng-show="menuIndex === 2">
    <a href="${pageContext.request.contextPath}/database_sql.html?token=${requestScope.command.request_token}">
        <img alt="${lbl_query_editor}" class="icon" src="${pageContext.request.contextPath}/components/icons/text-file.png">
		${lbl_query_editor}
	</a>
</li>
<m:store name="lbl_export" key="lbl.export" />
<li ng-show="menuIndex === 2">
    <a href="${pageContext.request.contextPath}/database_export.html?token=${requestScope.command.request_token}">
        <img alt="${lbl_export}" class="icon" src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
		${lbl_export}
	</a>
</li>
<m:store name="lbl_import" key="lbl.import" />
<li ng-show="menuIndex === 2">
    <a href="${pageContext.request.contextPath}/database_import.html?token=${requestScope.command.request_token}">
        <img alt="${lbl_import}" class="icon" src="${pageContext.request.contextPath}/components/icons/import-g.png">
		${lbl_import}
	</a>
</li>
<m:store name="lbl_users_privileges" key="lbl.users_privileges" />
<li ng-show="menuIndex === 2">
    <a href="${pageContext.request.contextPath}/database_privileges.html?token=${requestScope.command.request_token}">
        <img alt="${lbl_users_privileges}" class="icon" src="${pageContext.request.contextPath}/components/icons/users.png">
		${lbl_users_privileges}
	</a>
</li>