/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.cdi.URLMapping;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.gedoplan.webclients.model.User;
import de.gedoplan.webclients.system.CurrentUser;
import de.gedoplan.webclients.vaadin.views.ErrorView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletException;

/**
 *
 * @author hjungnitsch
 */
@CDIUI(Konstanten.VAADIN_UI_PATH)
@Theme("demo")
@PreserveOnRefresh
@URLMapping(value = Konstanten.VAADIN_SERVLET_PATH)
public class VaadinDemoUi extends UI {

    @Inject
    private CDIViewProvider cDIViewProvider;

    @Inject
    private JaasAccessControl accessControl;

    @Inject
    @CurrentUser
    private Provider<User> userProvider;

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        if (!accessControl.isUserSignedIn()) {
            close();
            Page.getCurrent().setLocation(Konstanten.VAADIN_LOGIN_PATH);
            return;
        }
        VerticalLayout page = new VerticalLayout();
        setContent(page);
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        page.addComponents(createMenu(), content, createFooter());
        page.setExpandRatio(content, 1);
        page.setSizeFull();

        navigator = new Navigator(this, content);
        navigator.addProvider(cDIViewProvider);
        navigator.setErrorView(ErrorView.class);
    }


    public MenuBar createMenu() {
        User user = userProvider.get();
        MenuBar mainMenu = new MenuBar();
        mainMenu.setWidth(100, Unit.PERCENTAGE);
        mainMenu.setStyleName("main");
        mainMenu.addItem("", new ThemeResource("images/vaadin-logo.png"), e -> navigator.navigateTo(Konstanten.VAADIN_VIEW_INDEX)).setStyleName("logo");
        if (user.isInRole(User.UserRole.ADMIN)) {
            mainMenu.addItem(Messages.menu_customer.value(), e -> navigator.navigateTo(Konstanten.VAADIN_VIEW_CUSTOMERS));
        }
        mainMenu.addItem(Messages.menu_orders.value(), e -> navigator.navigateTo(Konstanten.VAADIN_VIEW_ORDERS));
        if (user.isInRole(User.UserRole.CUSTOMER)) {
            mainMenu.addItem(Messages.menu_customerdetails.value(), e -> navigator.navigateTo(Konstanten.VAADIN_VIEW_CUSTOMER_DETAILS + "/" + user.getCustomerID()));
        }
        MenuBar.MenuItem userMenu = mainMenu.addItem(user.getLogin(), null);
        userMenu.setStyleName("align-right");
        userMenu.addItem(Messages.logout.value(), e -> {
            try {
                JaasAccessControl.logout();
                VaadinSession.getCurrent().close();
                Page.getCurrent().setLocation(Konstanten.VAADIN_LOGIN_PATH);
            } catch (ServletException ex) {
                Logger.getLogger(VaadinDemoUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return mainMenu;
    }

    public Panel createFooter() {
        Panel footer = new Panel();
        footer.setStyleName(ValoTheme.PANEL_WELL);
        footer.setHeight(75, Unit.PIXELS);
        Label footerText = new Label("© 2016 gedoplan.de");
        footerText.setSizeUndefined();
        VerticalLayout footerLayout = new VerticalLayout();
        footerLayout.setSizeFull();
        footerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        footerLayout.addComponent(footerText);
        footer.setContent(footerLayout);
        return footer;
    }


    @NormalUIScoped
    @Produces
    protected Navigator exposeNavigator() {
        return navigator;
    }

}
