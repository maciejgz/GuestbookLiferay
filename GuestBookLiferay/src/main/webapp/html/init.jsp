<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="theme" %>

<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="pl.mg.lf.guestbook.model.Guestbook" %>
<%@ page import="pl.mg.lf.guestbook.service.EntryLocalServiceUtil" %>
<%@ page import="pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil" %>

<portlet:defineObjects />
<theme:defineObjects />