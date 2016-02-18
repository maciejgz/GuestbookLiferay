package pl.mg.lf.guestbook.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import pl.mg.lf.guestbook.model.Guestbook;
import pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil;

/**
 * @author mg
 * @generated
 */
public abstract class GuestbookActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public GuestbookActionableDynamicQuery() throws SystemException {
        setBaseLocalService(GuestbookLocalServiceUtil.getService());
        setClass(Guestbook.class);

        setClassLoader(pl.mg.lf.guestbook.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("guestbookId");
    }
}
