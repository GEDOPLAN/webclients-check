/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.gedoplan.webclients.vaadin.Konstanten;

/**
 *
 * @author hjungnitsch
 */
@CDIView(Konstanten.VAADIN_VIEW_INDEX)
public class IndexView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (getComponentCount() > 0) {
            return;
        }
        setSizeFull();
        setMargin(true);
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        Label text = new Label("Demo Anwendung Vaadin");
        text.setWidthUndefined();
        text.setStyleName(ValoTheme.LABEL_H1);
        addComponent(text);
    }

}
