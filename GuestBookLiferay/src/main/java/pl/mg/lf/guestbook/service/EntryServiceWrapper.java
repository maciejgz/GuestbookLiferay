package pl.mg.lf.guestbook.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EntryService}.
 *
 * @author mg
 * @see EntryService
 * @generated
 */
public class EntryServiceWrapper implements EntryService,
    ServiceWrapper<EntryService> {
    private EntryService _entryService;

    public EntryServiceWrapper(EntryService entryService) {
        _entryService = entryService;
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    @Override
    public java.lang.String getBeanIdentifier() {
        return _entryService.getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    @Override
    public void setBeanIdentifier(java.lang.String beanIdentifier) {
        _entryService.setBeanIdentifier(beanIdentifier);
    }

    @Override
    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return _entryService.invokeMethod(name, parameterTypes, arguments);
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
     */
    public EntryService getWrappedEntryService() {
        return _entryService;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
     */
    public void setWrappedEntryService(EntryService entryService) {
        _entryService = entryService;
    }

    @Override
    public EntryService getWrappedService() {
        return _entryService;
    }

    @Override
    public void setWrappedService(EntryService entryService) {
        _entryService = entryService;
    }
}
