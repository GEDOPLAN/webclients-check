/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.base;

import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.Notification;
import de.gedoplan.webclients.vaadin.Messages;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBAccessException;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author hjungnitsch
 */
@ApplicationScoped
public class ErrorHandler extends DefaultErrorHandler {

    private Logger logger = Logger.getLogger(ErrorHandler.class.getCanonicalName());

    @Override
    public void error(ErrorEvent event) {
        Throwable throwable = findRelevantThrowable(event.getThrowable());
        if (throwable instanceof EJBAccessException) {
            Notification.show(Messages.notallowed.value(), Notification.Type.ERROR_MESSAGE);
            logger.warning("Unberechtigter Zugriff verhindert!");
        } else {
            logger.log(Level.SEVERE, "Es ist ein interner Fehler aufgetreten", throwable);
            Notification.show("Es ist ein interner Fehler aufgetreten", Notification.Type.ERROR_MESSAGE);
        }
    }
}
