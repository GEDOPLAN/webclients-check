/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;
import de.gedoplan.webclients.model.Customer;
import de.gedoplan.webclients.model.Customer_;
import de.gedoplan.webclients.service.CustomerService;
import de.gedoplan.webclients.vaadin.Konstanten;
import de.gedoplan.webclients.vaadin.Messages;
import de.gedoplan.webclients.vaadin.base.BaseView;
import java.text.DecimalFormat;
import javax.inject.Inject;
import org.vaadin.viritin.layouts.MMarginInfo;

/**
 *
 * @author hjungnitsch
 */
@CDIView(value = Konstanten.VAADIN_VIEW_CUSTOMER_DETAILS, supportsParameters = true)
public class CustomerDetailView extends BaseView implements View {

    @Inject
    private CustomerService customerService;

    @Inject
    private CustomerForm form;

    private Customer customer;

    @Override
    public void myenter(ViewChangeListener.ViewChangeEvent event) {
        if (getComponentCount() > 0) {
            removeAllComponents();
        }
        customer = customerService.getCustomerById(event.getParameters());
        setWidth(100, Unit.PERCENTAGE);
        if (customer == null) {
            initNoCustomer();
            return;
        }
        form.setCustomer(customer);
        init();
    }

    public void init() {
        Double discount = customerService.calculateCustomerDiscount(customer.getCustomerID()).getDiscount();
        Label name = new Label(new PropertyFormatter(form.getProperty(Customer_.companyName)) {
            @Override
            public String format(Object value) {
                return value + " (" + customer.getCustomerID() + ")";
            }

            @Override
            public Object parse(String formattedValue) throws Exception {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        name.setStyleName(ValoTheme.LABEL_BOLD);
        Label rabattLabel = new Label(Messages.customer_discount.value());
        rabattLabel.setStyleName(ValoTheme.LABEL_BOLD);
        rabattLabel.setSizeUndefined();
        Label rabatt = new Label(new DecimalFormat("#0.00").format(discount) + "%");
        rabatt.setSizeUndefined();
        rabatt.addStyleName(ValoTheme.LABEL_COLORED);
        rabatt.addStyleName(ValoTheme.LABEL_BOLD);
        HorizontalLayout headline = new HorizontalLayout(name, rabattLabel, rabatt);
        headline.setComponentAlignment(rabatt, Alignment.TOP_RIGHT);
        headline.setExpandRatio(name, 1);
        headline.setWidth(100, Unit.PERCENTAGE);
        headline.setSpacing(true);
        headline.setStyleName(ValoTheme.LAYOUT_WELL);
        headline.setMargin(new MMarginInfo(false, true));
        Panel panel = new Panel();
        panel.setContent(form);
        setMargin(true);
        setSpacing(true);
        addComponents(headline, panel);
    }

    public void initNoCustomer() {
        Label error = new Label("Benutzer nicht gefunden !");
        error.setSizeUndefined();
        error.setStyleName(ValoTheme.LABEL_FAILURE);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(error);
    }

}
