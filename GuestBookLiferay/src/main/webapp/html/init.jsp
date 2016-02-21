<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="theme"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>

<%@ page import="java.util.List"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@ page import="com.liferay.portal.kernel.util.StringPool"%>
<%@ page import="pl.mg.lf.guestbook.model.Guestbook"%>
<%@ page import="pl.mg.lf.guestbook.service.EntryLocalServiceUtil"%>
<%@ page import="pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil"%>

<%@ page
	import="pl.mg.lf.guestbook.service.permission.GuestbookModelPermission"%>
<%@ page
	import="pl.mg.lf.guestbook.service.permission.GuestbookPermission"%>
<%@ page import="pl.mg.lf.guestbook.service.permission.EntryPermission"%>

<%@ page import="com.liferay.portal.kernel.dao.search.SearchEntry" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.security.permission.ActionKeys" %>
<%@ page import="pl.mg.lf.guestbook.model.Entry" %>
<%@ page import="pl.mg.lf.guestbook.util.WebKeys" %>


<portlet:defineObjects />
<theme:defineObjects />