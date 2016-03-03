/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.URLMapping;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.servlet.ServletException;

/**
 *
 * @author hjungnitsch
 */
@CDIUI(Konstanten.VAADIN_LOGIN_PATH)
@Theme("demo")
@URLMapping(Konstanten.VAADIN_SERVLET_PATH)
public class LoginUi extends UI {

    @Override
    protected void init(VaadinRequest request) {
        TextField name = new TextField(Messages.login_name.value());
        name.focus();
        PasswordField password = new PasswordField(Messages.login_password.value());
        Button login = new Button(Messages.login_submit.value(), e -> {
            try {
                JaasAccessControl.login(name.getValue(), password.getValue());
                Page.getCurrent().setLocation(Konstanten.VAADIN_UI_PATH);
            } catch (ServletException ex) {
                Notification.show(Messages.login_invalid.value(), Notification.Type.ERROR_MESSAGE);
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        FormLayout fieldLayout = new FormLayout(name, password, login);
        fieldLayout.setMargin(true);
        fieldLayout.setSpacing(true);
        Panel loginPanel = new Panel(Messages.login_title.value(), fieldLayout);
        loginPanel.setSizeUndefined();
        VerticalLayout page = new VerticalLayout();
        page.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        page.addComponent(loginPanel);
        page.setSizeFull();
        setContent(page);
    }

}
