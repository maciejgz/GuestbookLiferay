package pl.mg.lf.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.mg.lf.guestbook.GuestbookNameException;
import pl.mg.lf.guestbook.model.Guestbook;
import pl.mg.lf.guestbook.service.base.GuestbookLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

/**
 * The implementation of the guestbook local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link pl.mg.lf.guestbook.service.GuestbookLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author mg
 * @see pl.mg.lf.guestbook.service.base.GuestbookLocalServiceBaseImpl
 * @see pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil
 */
public class GuestbookLocalServiceImpl extends GuestbookLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil} to access the
	 * guestbook local service.
	 */

	static final Logger logger = LogManager
			.getLogger(GuestbookLocalServiceImpl.class);

	public List<Guestbook> getGuestbooks(long groupId, int status) throws SystemException {
	    return guestbookPersistence.findByG_S(groupId, WorkflowConstants.STATUS_APPROVED);
	}

	public List<Guestbook> getGuestbooks(long groupId, int start, int end)
			throws SystemException {
		return guestbookPersistence.findByGroupId(groupId, start, end);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new GuestbookNameException();
		}
	}

	public Guestbook addGuestbook(long userId, String name,
			ServiceContext serviceContext) throws SystemException,
			PortalException {
		logger.debug("add guestbook");
		long groupId = serviceContext.getScopeGroupId();

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = new Date();

		validate(name);

		long guestbookId = counterLocalService.increment();

		Guestbook guestbook = guestbookPersistence.create(guestbookId);

		guestbook.setUuid(serviceContext.getUuid());
		guestbook.setUserId(userId);
		guestbook.setGroupId(groupId);
		guestbook.setCompanyId(user.getCompanyId());
		guestbook.setUserName(user.getFullName());
		guestbook.setCreateDate(serviceContext.getCreateDate(now));
		guestbook.setModifiedDate(serviceContext.getModifiedDate(now));
		guestbook.setName(name);
		guestbook.setExpandoBridgeAttributes(serviceContext);
		guestbook.setStatus(WorkflowConstants.STATUS_DRAFT);

		guestbookPersistence.update(guestbook);

		// permissions
		resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
				Guestbook.class.getName(), guestbookId, false, true, true);
		
	
		//workflow
		WorkflowHandlerRegistryUtil.startWorkflowInstance(guestbook.getCompanyId(), 
		         guestbook.getGroupId(), guestbook.getUserId(), Guestbook.class.getName(), 
		         guestbook.getPrimaryKey(), guestbook, serviceContext);

		return guestbook;
	}
	
	public Guestbook updateStatus(long userId, long guestbookId, int status,
		       ServiceContext serviceContext) throws PortalException,
		       SystemException {

		    User user = userLocalService.getUser(userId);
		    Guestbook guestbook = getGuestbook(guestbookId);

		    guestbook.setStatus(status);
		    guestbook.setStatusByUserId(userId);
		    guestbook.setStatusByUserName(user.getFullName());
		    guestbook.setStatusDate(new Date());

		    guestbookPersistence.update(guestbook);

		    if (status == WorkflowConstants.STATUS_APPROVED) {

		       assetEntryLocalService.updateVisible(Guestbook.class.getName(),
		          guestbookId, true);

		    } else {

		       assetEntryLocalService.updateVisible(Guestbook.class.getName(),
		          guestbookId, false);
		    }

		    return guestbook;
		}
}
