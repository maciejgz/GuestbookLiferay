<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@include file="/html/init.jsp"%>

<%
    long guestbookId = Long.valueOf((Long) renderRequest
            .getAttribute("guestbookId"));
%>

<%
	Guestbook guestbook = (Guestbook) renderRequest
			.getAttribute(WebKeys.GUESTBOOK);
%>



<jsp:useBean id="entries" class="java.util.ArrayList" scope="request" />



<aui:nav cssClass="nav-tabs">

	<%
		Logger logger = LogManager.getLogger(Guestbook.class);
        List<Guestbook> guestbooks = GuestbookLocalServiceUtil
                    .getGuestbooks(scopeGroupId,WorkflowConstants.STATUS_APPROVED);
            for (int i = 0; i < guestbooks.size(); i++) {
                Guestbook curGuestbook = (Guestbook) guestbooks.get(i);
                String cssClass = StringPool.BLANK;

                if (curGuestbook.getGuestbookId() == guestbookId) {
                    cssClass = "active";
                }
                
                if (GuestbookPermission.contains(
            		    permissionChecker, curGuestbook.getGuestbookId(), "VIEW")) {
    %>

	<portlet:renderURL var="viewPageURL">
		<portlet:param name="mvcPath" value="/html/guestbook/view.jsp" />
		<portlet:param name="guestbookId"
			value="<%=String.valueOf(curGuestbook.getGuestbookId())%>" />
	</portlet:renderURL>

	<aui:nav-item cssClass="<%=cssClass%>" href="<%=viewPageURL%>"
		label="<%=HtmlUtil.escape(curGuestbook.getName())%>" />

	<%
                }
        }
    %>

</aui:nav>

<aui:button-row cssClass="guestbook-buttons">

	<c:if
		test='<%= GuestbookModelPermission.contains(permissionChecker, scopeGroupId, "ADD_GUESTBOOK") %>'>

		<portlet:renderURL var="addGuestbookURL">
			<portlet:param name="mvcPath"
				value="/html/guestbook/edit_guestbook.jsp" />
		</portlet:renderURL>

		<aui:button onClick="<%=addGuestbookURL.toString()%>"
			value="Add Guestbook" />
	</c:if>
	<c:if
		test='<%= GuestbookPermission.contains(permissionChecker, guestbookId, "ADD_ENTRY") %>'>
		<portlet:renderURL var="addEntryURL">
			<portlet:param name="mvcPath" value="/html/guestbook/edit_entry.jsp" />
			<portlet:param name="guestbookId"
				value="<%=String.valueOf(guestbookId)%>" />
		</portlet:renderURL>

		<aui:button onClick="<%=addEntryURL.toString()%>" value="Add Entry"></aui:button>
	</c:if>


</aui:button-row>

<liferay-ui:search-container>
	<liferay-ui:search-container-results 
		results="<%=EntryLocalServiceUtil.getEntries(scopeGroupId,
				guestbookId, WorkflowConstants.STATUS_APPROVED,searchContainer.getStart(),
						searchContainer.getEnd())%>"
		total="<%=EntryLocalServiceUtil.getEntriesCount(scopeGroupId,
				guestbookId, WorkflowConstants.STATUS_APPROVED)%>" />

	<liferay-ui:search-container-row
		className="pl.mg.lf.guestbook.model.Entry" modelVar="entry">

		<liferay-ui:search-container-column-text property="message" />

		<liferay-ui:search-container-column-text property="name" />

		<liferay-ui:search-container-column-jsp
			path="/html/guestbook/guestbook_actions.jsp" align="right" />

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>
