/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.gedoplan.webclients.model.Customer;
import de.gedoplan.webclients.model.Customer_;
import de.gedoplan.webclients.service.CustomerService;
import de.gedoplan.webclients.vaadin.base.DemoFieldGroup;
import de.gedoplan.webclients.vaadin.Konstanten;
import de.gedoplan.webclients.vaadin.Messages;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author hjungnitsch
 */
@Dependent
public class CustomerForm extends VerticalLayout {

    private TextField id;
    private TextField name;
    private TextField kontakt;
    private TextField strasse;
    private TextField plz;
    private TextField stadt;
    private TextField region;
    private TextField land;
    private TextField telefon;
    private TextField fax;

    private Button speichern;
    private Button abbrechen;

    @Inject
    private CustomerService customerService;
    @Inject
    private Navigator navigator;

    private DemoFieldGroup<Customer> fieldGroup;

    @PostConstruct
    protected void init() {
        fieldGroup = new DemoFieldGroup<>(Customer.class);
        fieldGroup.setBuffered(true);
        bind();
        fieldGroup.configureNotNull();
        configureFields();
        configureActions();
        buildLayout();
    }

    protected void buildLayout() {
        GridLayout gridLayout = new GridLayout(4, 4);
        gridLayout.setSpacing(true);
        gridLayout.setMargin(true);
        gridLayout.setWidth(100, Unit.PERCENTAGE);
        gridLayout.addComponent(id, 0, 0);
        gridLayout.addComponent(name, 1, 0, 2, 0);
        gridLayout.addComponent(kontakt, 3, 0);
        gridLayout.addComponent(strasse, 0, 1);
        gridLayout.addComponent(plz, 1, 1);
        gridLayout.addComponent(stadt, 2, 1, 3, 1);
        gridLayout.addComponent(region, 0, 2);
        gridLayout.addComponent(land, 1, 2);
        gridLayout.addComponent(telefon, 2, 2);
        gridLayout.addComponent(fax, 3, 2);
        gridLayout.addComponent(speichern, 1, 3);
        gridLayout.addComponent(abbrechen, 2, 3);
        gridLayout.setComponentAlignment(speichern, Alignment.MIDDLE_CENTER);
        gridLayout.setComponentAlignment(abbrechen, Alignment.MIDDLE_CENTER);
        speichern.setWidth(200, Unit.PIXELS);
        abbrechen.setWidth(200, Unit.PIXELS);
        setSpacing(true);
        setWidth(100, Unit.PERCENTAGE);
        addComponent(gridLayout);
    }

    protected void configureFields() {
        id.setReadOnly(true);
        fieldGroup.getFields().stream().forEach((field) -> {
            field.setWidth(100, Unit.PERCENTAGE);
            field.setStyleName("form");
        });
    }

    protected void bind() {
        id = bind(Messages.customer_customerID, Customer_.customerID, TextField.class);
        name = bind(Messages.customer_companyName, Customer_.companyName, TextField.class);
        kontakt = bind(Messages.customer_contactName, Customer_.contactName, TextField.class);
        strasse = bind(Messages.customer_address, Customer_.address, TextField.class);
        plz = bind(Messages.customer_postalCode, Customer_.postalCode, TextField.class);
        stadt = bind(Messages.customer_city, Customer_.city, TextField.class);
        region = bind(Messages.customer_region, Customer_.region, TextField.class);
        land = bind(Messages.customer_country, Customer_.country, TextField.class);
        telefon = bind(Messages.customer_phone, Customer_.phone, TextField.class);
        fax = bind(Messages.customer_fax, Customer_.fax, TextField.class);
    }

    protected <T extends Field> T bind(Messages caption, SingularAttribute<?, ?> property, Class<T> type) {
        return (T) fieldGroup.buildAndBind(caption.value(), property.getName(), TextField.class);
    }

    public void setCustomer(Customer customer) {
        fieldGroup.setItemDataSource(customer);
    }

    public Customer getCustomer() {
        return fieldGroup.getItemDataSource().getBean();
    }

    private void configureActions() {
        speichern = new Button(Messages.save.value(), e -> {
            try {
                fieldGroup.commit();
                fieldGroup.setItemDataSource(customerService.updateCustomer(fieldGroup.getItemDataSource().getBean()));
                Notification success = new Notification(Messages.save_success.value());
                success.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
                success.show(Page.getCurrent());
            } catch (FieldGroup.CommitException ex) {
                // Leer
            }
        });
        abbrechen = new Button(Messages.cancel.value(), e -> navigator.navigateTo(Konstanten.VAADIN_VIEW_CUSTOMERS));
    }

    public Property<?> getProperty(SingularAttribute<?, ?> property) {
        return fieldGroup.getField(property.getName());
    }
}
