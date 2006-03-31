package org.appfuse.webapp.action;

import java.io.IOException;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.appfuse.webapp.listener.StartupListener;

public abstract class Reload extends BasePage implements PageBeginRenderListener {
    public abstract IEngineService getEngineService();

    public ILink execute(IRequestCycle cycle) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'execute' method");
        }

        StartupListener.setupContext(getServletContext());
        MainMenu nextPage = (MainMenu) cycle.getPage("mainMenu");
        nextPage.setMessage(getText("reload.succeeded"));            
        return getEngineService().getLink(false, nextPage.getPageName());
    }
}
