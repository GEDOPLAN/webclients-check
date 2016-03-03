/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import de.gedoplan.webclients.vaadin.base.BaseView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author hjungnitsch
 */
public class ErrorView extends BaseView {

    @Override
    public void myenter(ViewChangeListener.ViewChangeEvent event) {
        Label error = new Label("Seite konnte nicht gefunden werden !");
        error.setSizeUndefined();
        error.setStyleName(ValoTheme.LABEL_FAILURE);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(error);
        setSizeFull();
    }

}
