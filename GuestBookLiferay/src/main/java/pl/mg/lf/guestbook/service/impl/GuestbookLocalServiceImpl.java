package pl.mg.lf.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import pl.mg.lf.guestbook.GuestbookNameException;
import pl.mg.lf.guestbook.model.Guestbook;
import pl.mg.lf.guestbook.service.base.GuestbookLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
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

	public List<Guestbook> getGuestbooks(long groupId) throws SystemException {
		return guestbookPersistence.findByGroupId(groupId);
	}

	public List<Guestbook> getGuestbooks(long groupId, int start, int end)
			throws SystemException {
		return guestbookPersistence.findByGroupId(groupId, start, end);
	}
	
	protected void validate (String name) throws PortalException {
	    if (Validator.isNull(name)) {
	       throw new GuestbookNameException();
	    }
	}
	
	public Guestbook addGuestbook(long userId, String name, 
		    ServiceContext serviceContext) throws SystemException, PortalException {
		
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

		guestbookPersistence.update(guestbook);
		
		
		//permissions
		resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
			       Guestbook.class.getName(), guestbookId, false, true, true);

		return guestbook;
	}
}
