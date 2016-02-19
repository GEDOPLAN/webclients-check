package de.gedoplan.jaxgui.jsf.system;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBAccessException;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class ApplicationExceptionHandler extends ExceptionHandlerWrapper {

    private static final Logger log = Logger.getLogger(ApplicationExceptionHandler.class.getCanonicalName());
    private ExceptionHandler wrapped;

    ApplicationExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context
                    = (ExceptionQueuedEventContext) event.getSource();

            Throwable t = context.getException();
            if (t instanceof EJBAccessException) {
                final FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "login");
                i.remove();
            }

            getWrapped().handle();
        }
    }
}
