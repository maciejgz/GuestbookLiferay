package pl.mg.lf.guestbook.service.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import pl.mg.lf.guestbook.service.ClpSerializer;
import pl.mg.lf.guestbook.service.EntryLocalServiceUtil;
import pl.mg.lf.guestbook.service.EntryServiceUtil;
import pl.mg.lf.guestbook.service.GuestbookLocalServiceUtil;
import pl.mg.lf.guestbook.service.GuestbookServiceUtil;


public class ClpMessageListener extends BaseMessageListener {
    public static String getServletContextName() {
        return ClpSerializer.getServletContextName();
    }

    @Override
    protected void doReceive(Message message) throws Exception {
        String command = message.getString("command");
        String servletContextName = message.getString("servletContextName");

        if (command.equals("undeploy") &&
                servletContextName.equals(getServletContextName())) {
            EntryLocalServiceUtil.clearService();

            EntryServiceUtil.clearService();
            GuestbookLocalServiceUtil.clearService();

            GuestbookServiceUtil.clearService();
        }
    }
}
