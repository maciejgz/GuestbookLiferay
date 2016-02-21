package pl.mg.lf.guestbook.workflow;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import pl.mg.lf.guestbook.model.Guestbook;
import pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;

public class GuestbookWorkflowhandler extends BaseWorkflowHandler {

	private static final String CLASS_NAME = Guestbook.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public Object updateStatus(int status,
			Map<String, Serializable> workflowContext) throws PortalException,
			SystemException {
		 long userId = GetterUtil.getLong(
	                (String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
	            long guestbookId = GetterUtil.getLong(
	                (String)workflowContext.get(
	                    WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

	            ServiceContext serviceContext = (ServiceContext)workflowContext.get(
	                "serviceContext");
	            
	            
//TODO return
//	            return GuestbookLocalServiceUtil.updateStatus(
//	                userId, guestbookId, status, serviceContext);
	            return null;
	}

}
