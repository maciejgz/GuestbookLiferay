package pl.mg.lf.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import pl.mg.lf.guestbook.EntryEmailException;
import pl.mg.lf.guestbook.EntryMessageException;
import pl.mg.lf.guestbook.EntryNameException;
import pl.mg.lf.guestbook.model.Entry;
import pl.mg.lf.guestbook.service.base.EntryLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

/**
 * The implementation of the entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link pl.mg.lf.guestbook.service.EntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author mg
 * @see pl.mg.lf.guestbook.service.base.EntryLocalServiceBaseImpl
 * @see pl.mg.lf.guestbook.service.EntryLocalServiceUtil
 */
public class EntryLocalServiceImpl extends EntryLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * pl.mg.lf.guestbook.service.EntryLocalServiceUtil} to access the entry
	 * local service.
	 */

	public List<Entry> getEntries(long groupId, long guestbookId, int status,
			int start, int end) throws SystemException {
		return entryPersistence.findByG_G_S(groupId, guestbookId,
				WorkflowConstants.STATUS_APPROVED, start, end);
	}

	public int getEntriesCount(long groupId, long guestbookId, int status)
			throws SystemException {
		return entryPersistence.countByG_G_S(groupId, guestbookId,
				WorkflowConstants.STATUS_APPROVED);
	}

	protected void validate(String name, String email, String entry)
			throws PortalException {
		if (Validator.isNull(name)) {
			throw new EntryNameException();
		}

		if (!Validator.isEmailAddress(email)) {
			throw new EntryEmailException();
		}

		if (Validator.isNull(entry)) {
			throw new EntryMessageException();
		}
	}

	public Entry addEntry(long userId, long guestbookId, String name,
			String email, String message, ServiceContext serviceContext)
			throws PortalException, SystemException {
		long groupId = serviceContext.getScopeGroupId();

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = new Date();

		validate(name, email, message);

		long entryId = counterLocalService.increment();

		Entry entry = entryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setUserId(userId);
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setExpandoBridgeAttributes(serviceContext);
		entry.setGuestbookId(guestbookId);
		entry.setName(name);
		entry.setEmail(email);
		entry.setMessage(message);

		entryPersistence.update(entry);

		resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
				Entry.class.getName(), entryId, false, true, true);

		return entry;
	}

	public Entry deleteEntry(long entryId, ServiceContext serviceContext)
			throws PortalException, SystemException {

		Entry entry = getEntry(entryId);

		resourceLocalService.deleteResource(serviceContext.getCompanyId(),
				Entry.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
				entryId);

		entry = deleteEntry(entryId);

		return entry;
	}

	public Entry updateEntry(long userId, long guestbookId, long entryId,
			String name, String email, String message,
			ServiceContext serviceContext) throws PortalException,
			SystemException {

		long groupId = serviceContext.getScopeGroupId();

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = new Date();

		validate(name, email, message);

		Entry entry = getEntry(entryId);

		entry.setUserId(userId);
		entry.setUserName(user.getFullName());
		entry.setName(name);
		entry.setEmail(email);
		entry.setMessage(message);
		entry.setStatus(WorkflowConstants.STATUS_DRAFT);
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setExpandoBridgeAttributes(serviceContext);

		entryPersistence.update(entry);

		resourceLocalService.updateResources(user.getCompanyId(), groupId,
				Entry.class.getName(), entryId,
				serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());

		WorkflowHandlerRegistryUtil.startWorkflowInstance(entry.getCompanyId(),
				entry.getGroupId(), entry.getUserId(), Entry.class.getName(),
				entry.getPrimaryKey(), entry, serviceContext);

		return entry;
	}

	public Entry updateStatus(long userId, long entryId, int status,
			ServiceContext serviceContext) throws PortalException,
			SystemException {

		User user = userLocalService.getUser(userId);
		Entry entry = getEntry(entryId);

		entry.setStatus(status);
		entry.setStatusByUserId(userId);
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(new Date());

		entryPersistence.update(entry);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			assetEntryLocalService.updateVisible(Entry.class.getName(),
					entryId, true);

		} else {

			assetEntryLocalService.updateVisible(Entry.class.getName(),
					entryId, false);
		}

		return entry;
	}

}
