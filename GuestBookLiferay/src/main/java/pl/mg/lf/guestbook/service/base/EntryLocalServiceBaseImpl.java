package pl.mg.lf.guestbook.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import pl.mg.lf.guestbook.model.Entry;
import pl.mg.lf.guestbook.service.EntryLocalService;
import pl.mg.lf.guestbook.service.persistence.EntryPersistence;
import pl.mg.lf.guestbook.service.persistence.GuestbookPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link pl.mg.lf.guestbook.service.impl.EntryLocalServiceImpl}.
 * </p>
 *
 * @author mg
 * @see pl.mg.lf.guestbook.service.impl.EntryLocalServiceImpl
 * @see pl.mg.lf.guestbook.service.EntryLocalServiceUtil
 * @generated
 */
public abstract class EntryLocalServiceBaseImpl extends BaseLocalServiceImpl
    implements EntryLocalService, IdentifiableBean {
    @BeanReference(type = pl.mg.lf.guestbook.service.EntryLocalService.class)
    protected pl.mg.lf.guestbook.service.EntryLocalService entryLocalService;
    @BeanReference(type = pl.mg.lf.guestbook.service.EntryService.class)
    protected pl.mg.lf.guestbook.service.EntryService entryService;
    @BeanReference(type = EntryPersistence.class)
    protected EntryPersistence entryPersistence;
    @BeanReference(type = pl.mg.lf.guestbook.service.GuestbookLocalService.class)
    protected pl.mg.lf.guestbook.service.GuestbookLocalService guestbookLocalService;
    @BeanReference(type = pl.mg.lf.guestbook.service.GuestbookService.class)
    protected pl.mg.lf.guestbook.service.GuestbookService guestbookService;
    @BeanReference(type = GuestbookPersistence.class)
    protected GuestbookPersistence guestbookPersistence;
    @BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
    protected com.liferay.counter.service.CounterLocalService counterLocalService;
    @BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
    protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
    @BeanReference(type = com.liferay.portal.service.UserLocalService.class)
    protected com.liferay.portal.service.UserLocalService userLocalService;
    @BeanReference(type = com.liferay.portal.service.UserService.class)
    protected com.liferay.portal.service.UserService userService;
    @BeanReference(type = UserPersistence.class)
    protected UserPersistence userPersistence;
    private String _beanIdentifier;
    private ClassLoader _classLoader;
    private EntryLocalServiceClpInvoker _clpInvoker = new EntryLocalServiceClpInvoker();

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. Always use {@link pl.mg.lf.guestbook.service.EntryLocalServiceUtil} to access the entry local service.
     */

    /**
     * Adds the entry to the database. Also notifies the appropriate model listeners.
     *
     * @param entry the entry
     * @return the entry that was added
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.REINDEX)
    @Override
    public Entry addEntry(Entry entry) throws SystemException {
        entry.setNew(true);

        return entryPersistence.update(entry);
    }

    /**
     * Creates a new entry with the primary key. Does not add the entry to the database.
     *
     * @param entryId the primary key for the new entry
     * @return the new entry
     */
    @Override
    public Entry createEntry(long entryId) {
        return entryPersistence.create(entryId);
    }

    /**
     * Deletes the entry with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param entryId the primary key of the entry
     * @return the entry that was removed
     * @throws PortalException if a entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.DELETE)
    @Override
    public Entry deleteEntry(long entryId)
        throws PortalException, SystemException {
        return entryPersistence.remove(entryId);
    }

    /**
     * Deletes the entry from the database. Also notifies the appropriate model listeners.
     *
     * @param entry the entry
     * @return the entry that was removed
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.DELETE)
    @Override
    public Entry deleteEntry(Entry entry) throws SystemException {
        return entryPersistence.remove(entry);
    }

    @Override
    public DynamicQuery dynamicQuery() {
        Class<?> clazz = getClass();

        return DynamicQueryFactoryUtil.forClass(Entry.class,
            clazz.getClassLoader());
    }

    /**
     * Performs a dynamic query on the database and returns the matching rows.
     *
     * @param dynamicQuery the dynamic query
     * @return the matching rows
     * @throws SystemException if a system exception occurred
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List dynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return entryPersistence.findWithDynamicQuery(dynamicQuery);
    }

    /**
     * Performs a dynamic query on the database and returns a range of the matching rows.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link pl.mg.lf.guestbook.model.impl.EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
     * </p>
     *
     * @param dynamicQuery the dynamic query
     * @param start the lower bound of the range of model instances
     * @param end the upper bound of the range of model instances (not inclusive)
     * @return the range of matching rows
     * @throws SystemException if a system exception occurred
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
        throws SystemException {
        return entryPersistence.findWithDynamicQuery(dynamicQuery, start, end);
    }

    /**
     * Performs a dynamic query on the database and returns an ordered range of the matching rows.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link pl.mg.lf.guestbook.model.impl.EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
     * </p>
     *
     * @param dynamicQuery the dynamic query
     * @param start the lower bound of the range of model instances
     * @param end the upper bound of the range of model instances (not inclusive)
     * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
     * @return the ordered range of matching rows
     * @throws SystemException if a system exception occurred
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        return entryPersistence.findWithDynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
     * Returns the number of rows that match the dynamic query.
     *
     * @param dynamicQuery the dynamic query
     * @return the number of rows that match the dynamic query
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long dynamicQueryCount(DynamicQuery dynamicQuery)
        throws SystemException {
        return entryPersistence.countWithDynamicQuery(dynamicQuery);
    }

    /**
     * Returns the number of rows that match the dynamic query.
     *
     * @param dynamicQuery the dynamic query
     * @param projection the projection to apply to the query
     * @return the number of rows that match the dynamic query
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long dynamicQueryCount(DynamicQuery dynamicQuery,
        Projection projection) throws SystemException {
        return entryPersistence.countWithDynamicQuery(dynamicQuery, projection);
    }

    @Override
    public Entry fetchEntry(long entryId) throws SystemException {
        return entryPersistence.fetchByPrimaryKey(entryId);
    }

    /**
     * Returns the entry with the matching UUID and company.
     *
     * @param uuid the entry's UUID
     * @param  companyId the primary key of the company
     * @return the matching entry, or <code>null</code> if a matching entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Entry fetchEntryByUuidAndCompanyId(String uuid, long companyId)
        throws SystemException {
        return entryPersistence.fetchByUuid_C_First(uuid, companyId, null);
    }

    /**
     * Returns the entry matching the UUID and group.
     *
     * @param uuid the entry's UUID
     * @param groupId the primary key of the group
     * @return the matching entry, or <code>null</code> if a matching entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Entry fetchEntryByUuidAndGroupId(String uuid, long groupId)
        throws SystemException {
        return entryPersistence.fetchByUUID_G(uuid, groupId);
    }

    /**
     * Returns the entry with the primary key.
     *
     * @param entryId the primary key of the entry
     * @return the entry
     * @throws PortalException if a entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Entry getEntry(long entryId) throws PortalException, SystemException {
        return entryPersistence.findByPrimaryKey(entryId);
    }

    @Override
    public PersistedModel getPersistedModel(Serializable primaryKeyObj)
        throws PortalException, SystemException {
        return entryPersistence.findByPrimaryKey(primaryKeyObj);
    }

    /**
     * Returns the entry with the matching UUID and company.
     *
     * @param uuid the entry's UUID
     * @param  companyId the primary key of the company
     * @return the matching entry
     * @throws PortalException if a matching entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Entry getEntryByUuidAndCompanyId(String uuid, long companyId)
        throws PortalException, SystemException {
        return entryPersistence.findByUuid_C_First(uuid, companyId, null);
    }

    /**
     * Returns the entry matching the UUID and group.
     *
     * @param uuid the entry's UUID
     * @param groupId the primary key of the group
     * @return the matching entry
     * @throws PortalException if a matching entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Entry getEntryByUuidAndGroupId(String uuid, long groupId)
        throws PortalException, SystemException {
        return entryPersistence.findByUUID_G(uuid, groupId);
    }

    /**
     * Returns a range of all the entries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link pl.mg.lf.guestbook.model.impl.EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
     * </p>
     *
     * @param start the lower bound of the range of entries
     * @param end the upper bound of the range of entries (not inclusive)
     * @return the range of entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<Entry> getEntries(int start, int end) throws SystemException {
        return entryPersistence.findAll(start, end);
    }

    /**
     * Returns the number of entries.
     *
     * @return the number of entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int getEntriesCount() throws SystemException {
        return entryPersistence.countAll();
    }

    /**
     * Updates the entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
     *
     * @param entry the entry
     * @return the entry that was updated
     * @throws SystemException if a system exception occurred
     */
    @Indexable(type = IndexableType.REINDEX)
    @Override
    public Entry updateEntry(Entry entry) throws SystemException {
        return entryPersistence.update(entry);
    }

    /**
     * Returns the entry local service.
     *
     * @return the entry local service
     */
    public pl.mg.lf.guestbook.service.EntryLocalService getEntryLocalService() {
        return entryLocalService;
    }

    /**
     * Sets the entry local service.
     *
     * @param entryLocalService the entry local service
     */
    public void setEntryLocalService(
        pl.mg.lf.guestbook.service.EntryLocalService entryLocalService) {
        this.entryLocalService = entryLocalService;
    }

    /**
     * Returns the entry remote service.
     *
     * @return the entry remote service
     */
    public pl.mg.lf.guestbook.service.EntryService getEntryService() {
        return entryService;
    }

    /**
     * Sets the entry remote service.
     *
     * @param entryService the entry remote service
     */
    public void setEntryService(
        pl.mg.lf.guestbook.service.EntryService entryService) {
        this.entryService = entryService;
    }

    /**
     * Returns the entry persistence.
     *
     * @return the entry persistence
     */
    public EntryPersistence getEntryPersistence() {
        return entryPersistence;
    }

    /**
     * Sets the entry persistence.
     *
     * @param entryPersistence the entry persistence
     */
    public void setEntryPersistence(EntryPersistence entryPersistence) {
        this.entryPersistence = entryPersistence;
    }

    /**
     * Returns the guestbook local service.
     *
     * @return the guestbook local service
     */
    public pl.mg.lf.guestbook.service.GuestbookLocalService getGuestbookLocalService() {
        return guestbookLocalService;
    }

    /**
     * Sets the guestbook local service.
     *
     * @param guestbookLocalService the guestbook local service
     */
    public void setGuestbookLocalService(
        pl.mg.lf.guestbook.service.GuestbookLocalService guestbookLocalService) {
        this.guestbookLocalService = guestbookLocalService;
    }

    /**
     * Returns the guestbook remote service.
     *
     * @return the guestbook remote service
     */
    public pl.mg.lf.guestbook.service.GuestbookService getGuestbookService() {
        return guestbookService;
    }

    /**
     * Sets the guestbook remote service.
     *
     * @param guestbookService the guestbook remote service
     */
    public void setGuestbookService(
        pl.mg.lf.guestbook.service.GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    /**
     * Returns the guestbook persistence.
     *
     * @return the guestbook persistence
     */
    public GuestbookPersistence getGuestbookPersistence() {
        return guestbookPersistence;
    }

    /**
     * Sets the guestbook persistence.
     *
     * @param guestbookPersistence the guestbook persistence
     */
    public void setGuestbookPersistence(
        GuestbookPersistence guestbookPersistence) {
        this.guestbookPersistence = guestbookPersistence;
    }

    /**
     * Returns the counter local service.
     *
     * @return the counter local service
     */
    public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
        return counterLocalService;
    }

    /**
     * Sets the counter local service.
     *
     * @param counterLocalService the counter local service
     */
    public void setCounterLocalService(
        com.liferay.counter.service.CounterLocalService counterLocalService) {
        this.counterLocalService = counterLocalService;
    }

    /**
     * Returns the resource local service.
     *
     * @return the resource local service
     */
    public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
        return resourceLocalService;
    }

    /**
     * Sets the resource local service.
     *
     * @param resourceLocalService the resource local service
     */
    public void setResourceLocalService(
        com.liferay.portal.service.ResourceLocalService resourceLocalService) {
        this.resourceLocalService = resourceLocalService;
    }

    /**
     * Returns the user local service.
     *
     * @return the user local service
     */
    public com.liferay.portal.service.UserLocalService getUserLocalService() {
        return userLocalService;
    }

    /**
     * Sets the user local service.
     *
     * @param userLocalService the user local service
     */
    public void setUserLocalService(
        com.liferay.portal.service.UserLocalService userLocalService) {
        this.userLocalService = userLocalService;
    }

    /**
     * Returns the user remote service.
     *
     * @return the user remote service
     */
    public com.liferay.portal.service.UserService getUserService() {
        return userService;
    }

    /**
     * Sets the user remote service.
     *
     * @param userService the user remote service
     */
    public void setUserService(
        com.liferay.portal.service.UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns the user persistence.
     *
     * @return the user persistence
     */
    public UserPersistence getUserPersistence() {
        return userPersistence;
    }

    /**
     * Sets the user persistence.
     *
     * @param userPersistence the user persistence
     */
    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public void afterPropertiesSet() {
        Class<?> clazz = getClass();

        _classLoader = clazz.getClassLoader();

        PersistedModelLocalServiceRegistryUtil.register("pl.mg.lf.guestbook.model.Entry",
            entryLocalService);
    }

    public void destroy() {
        PersistedModelLocalServiceRegistryUtil.unregister(
            "pl.mg.lf.guestbook.model.Entry");
    }

    /**
     * Returns the Spring bean ID for this bean.
     *
     * @return the Spring bean ID for this bean
     */
    @Override
    public String getBeanIdentifier() {
        return _beanIdentifier;
    }

    /**
     * Sets the Spring bean ID for this bean.
     *
     * @param beanIdentifier the Spring bean ID for this bean
     */
    @Override
    public void setBeanIdentifier(String beanIdentifier) {
        _beanIdentifier = beanIdentifier;
    }

    @Override
    public Object invokeMethod(String name, String[] parameterTypes,
        Object[] arguments) throws Throwable {
        Thread currentThread = Thread.currentThread();

        ClassLoader contextClassLoader = currentThread.getContextClassLoader();

        if (contextClassLoader != _classLoader) {
            currentThread.setContextClassLoader(_classLoader);
        }

        try {
            return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
        } finally {
            if (contextClassLoader != _classLoader) {
                currentThread.setContextClassLoader(contextClassLoader);
            }
        }
    }

    protected Class<?> getModelClass() {
        return Entry.class;
    }

    protected String getModelClassName() {
        return Entry.class.getName();
    }

    /**
     * Performs an SQL query.
     *
     * @param sql the sql query
     */
    protected void runSQL(String sql) throws SystemException {
        try {
            DataSource dataSource = entryPersistence.getDataSource();

            SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
                    sql, new int[0]);

            sqlUpdate.update();
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }
}
