package cz.cvut.fel.jee.exceptions.handler;

import cz.cvut.fel.jee.exceptions.AuthenticationException;
import cz.cvut.fel.jee.exceptions.NotFoundException;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tomáš on 28.1.14.
 */
public class ExceptionHandler extends ExceptionHandlerWrapper {

    private final javax.faces.context.ExceptionHandler wrapped;

    public ExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public javax.faces.context.ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            FacesContext fc = context.getContext();
            while ((t instanceof FacesException || t instanceof EJBException || t instanceof ELException)
                    && t.getCause() != null) {
                t = t.getCause();
            }
            boolean handled = handleException(t, fc);
            if (handled) i.remove();

        }
        getWrapped().handle();
    }

    private boolean handleException(Throwable exception, final FacesContext fc) {

        final ExternalContext externalContext = fc.getExternalContext();
        final Map<String, Object> requestMap = externalContext.getRequestMap();
        try {
            if (exception instanceof AuthenticationException) {
                externalContext.dispatch("/login.xhtml");
                fc.responseComplete();
                return true;
            } else if (exception instanceof NotFoundException) {
                externalContext.dispatch("/error/404.xhtml");
                fc.responseComplete();
                return true;
            } else if (exception instanceof javax.persistence.PersistenceException) {
                Logger.getLogger(ExceptionHandler.class.getName()).log(Level.SEVERE, exception.toString(), exception);
                return true;
            }
        } catch (final IOException e) {
            Logger.getLogger(ExceptionHandler.class.getName()).log(Level.SEVERE, e.toString(), e);
        }
        return false;
    }
}
