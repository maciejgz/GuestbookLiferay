package pl.mg.lf.guestbook.service.permission;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.mg.lf.guestbook.model.Guestbook;
import pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

public class GuestbookPermission {

	private static final Logger logger = LogManager
			.getLogger(GuestbookPermission.class);

	public static void check(PermissionChecker permissionChecker,
			long guestbookId, String actionId) throws PortalException,
			SystemException {

		if (!contains(permissionChecker, guestbookId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(PermissionChecker permissionChecker,
			long guestbookId, String actionId) throws PortalException,
			SystemException {

		Guestbook guestbook = GuestbookLocalServiceUtil
				.getGuestbook(guestbookId);

		boolean hasPermission = permissionChecker.hasPermission(
				guestbook.getGroupId(), Guestbook.class.getName(),
				guestbook.getGuestbookId(), actionId);

	/*	logger.debug("check if guestbook contains group); name=" + guestbook.getName()
				+ ";hasPermission=" + hasPermission);*/

		return hasPermission;

	}
}
