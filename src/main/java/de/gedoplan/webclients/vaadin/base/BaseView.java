/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.base;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author hjungnitsch
 */
public abstract class BaseView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            myenter(event);
        } catch (Exception e) {
            VaadinSession.getCurrent().getErrorHandler().error(new com.vaadin.server.ErrorEvent(e));
        }
    }

    public abstract void myenter(ViewChangeListener.ViewChangeEvent event);
}
