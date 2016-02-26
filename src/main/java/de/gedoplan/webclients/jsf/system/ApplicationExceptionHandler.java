package de.gedoplan.webclients.jsf.system;

import de.gedoplan.webclients.jsf.controller.BaseController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.ejb.EJBAccessException;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class ApplicationExceptionHandler extends ExceptionHandlerWrapper implements BaseController {

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

            EJBAccessException ejbAccessException = findException(EJBAccessException.class, context.getException());
            if (ejbAccessException != null) {
                String navigationpath;

                if (getRequest().getUserPrincipal() == null) {
                    navigationpath = "login";
                    getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), null, navigationpath);
                    i.remove();
                }
            }

            getWrapped().handle();
        }
    }

    private <T extends Throwable> T findException(Class<T> exceptionToFind, Throwable thrownException) {
        Throwable currentException = thrownException;
        do {
            if (currentException.getClass().isAssignableFrom(exceptionToFind)) {
                return (T) currentException;
            }

            currentException = currentException.getCause();

        } while (currentException != null);

        return null;
    }
}
