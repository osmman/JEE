package cz.cvut.fel.jee.exceptions.handler;

import cz.cvut.fel.jee.exceptions.AuthenticationException;
import cz.cvut.fel.jee.exceptions.NotFoundException;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import java.io.FileNotFoundException;
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
        for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext(); ) {
            Throwable t = it.next().getContext().getException();
            while ((t instanceof FacesException || t instanceof EJBException || t instanceof ELException)
                    && t.getCause() != null) {
                t = t.getCause();
            }
            try {
                handleException(t);
            } finally {
                it.remove();
            }
        }
        getWrapped().handle();
    }

    private void handleException(Throwable exception){

        if (exception instanceof FileNotFoundException || exception instanceof ViewExpiredException || exception instanceof NotFoundException) {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ExternalContext externalContext = facesContext.getExternalContext();
            final Map<String, Object> requestMap = externalContext.getRequestMap();

                String message;
                if (exception instanceof ViewExpiredException) {
                    final String viewId = ((ViewExpiredException) exception).getViewId();
                    message = "View is expired. <a href='/ifos" + viewId + "'>Back</a>";
                } else {
                    message = exception.getMessage(); // beware, don't leak internal info!
                }
                requestMap.put("errorMsg", message);
                try {
                    if(exception instanceof AuthenticationException){
                        externalContext.dispatch("/login.xhtml");
                    }else if (exception instanceof NotFoundException) {
                        externalContext.dispatch("/error/404.xhtml");
                    }
                } catch (final IOException e) {
                    Logger.getLogger(ExceptionHandler.class.getName()).log(Level.SEVERE, e.toString(), e);
                }
                facesContext.responseComplete();

        }
    }
}
