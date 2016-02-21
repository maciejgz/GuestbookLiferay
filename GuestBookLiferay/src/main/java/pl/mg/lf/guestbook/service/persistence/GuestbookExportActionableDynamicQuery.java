package pl.mg.lf.guestbook.service.persistence;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.util.PortalUtil;

import pl.mg.lf.guestbook.model.Guestbook;

/**
 * @author mg
 * @generated
 */
public class GuestbookExportActionableDynamicQuery
    extends GuestbookActionableDynamicQuery {
    private PortletDataContext _portletDataContext;

    public GuestbookExportActionableDynamicQuery(
        PortletDataContext portletDataContext) throws SystemException {
        _portletDataContext = portletDataContext;

        setCompanyId(_portletDataContext.getCompanyId());

        setGroupId(_portletDataContext.getScopeGroupId());
    }

    @Override
    public long performCount() throws PortalException, SystemException {
        ManifestSummary manifestSummary = _portletDataContext.getManifestSummary();

        StagedModelType stagedModelType = getStagedModelType();

        long modelAdditionCount = super.performCount();

        manifestSummary.addModelAdditionCount(stagedModelType.toString(),
            modelAdditionCount);

        long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(_portletDataContext,
                stagedModelType);

        manifestSummary.addModelDeletionCount(stagedModelType.toString(),
            modelDeletionCount);

        return modelAdditionCount;
    }

    @Override
    protected void addCriteria(DynamicQuery dynamicQuery) {
        Criterion modifiedDateCriterion = _portletDataContext.getDateRangeCriteria(
                "modifiedDate");
        Criterion statusDateCriterion = _portletDataContext.getDateRangeCriteria(
                "statusDate");

        if ((modifiedDateCriterion != null) && (statusDateCriterion != null)) {
            Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

            disjunction.add(modifiedDateCriterion);
            disjunction.add(statusDateCriterion);

            dynamicQuery.add(disjunction);
        }

        StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(Guestbook.class.getName());

        Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

        dynamicQuery.add(workflowStatusProperty.in(
                stagedModelDataHandler.getExportableStatuses()));
    }

    protected StagedModelType getStagedModelType() {
        return new StagedModelType(PortalUtil.getClassNameId(
                Guestbook.class.getName()));
    }

    @Override
    @SuppressWarnings("unused")
    protected void performAction(Object object)
        throws PortalException, SystemException {
        Guestbook stagedModel = (Guestbook) object;

        StagedModelDataHandlerUtil.exportStagedModel(_portletDataContext,
            stagedModel);
    }
}
