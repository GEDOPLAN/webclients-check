/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import de.gedoplan.webclients.model.Customer;
import de.gedoplan.webclients.model.Customer_;
import de.gedoplan.webclients.service.CustomerService;
import de.gedoplan.webclients.vaadin.Konstanten;
import de.gedoplan.webclients.vaadin.Messages;
import de.gedoplan.webclients.vaadin.base.MyQueryFactory;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author hjungnitsch
 */
@CDIView(Konstanten.VAADIN_VIEW_CUSTOMERS)
//@RolesAllowed(User.UserRole.ADMINS)
public class CustomersView extends BaseMasterView<Customer> implements View {

    @Inject
    private CustomerService customerService;
    @Inject
    private Navigator navigator;

    @Override
    protected void configureTable() {
        table.addGeneratedColumn(Customer_.customerID.getName(), (source, itemId, columnId)
                -> new Link(itemId.toString(), new ExternalResource("#!" + Konstanten.VAADIN_VIEW_CUSTOMER_DETAILS + "/" + itemId)));
//        table.getColumn(Customer_.customerID.getName()).setRenderer(new ButtonRenderer(e -> navigator.navigateTo(Konstanten.VAADIN_VIEW_CUSTOMER_DETAILS + "/" + e.getItemId())));
    }

    @Override
    public List<Field> getColumns() {
        return Arrays.asList(
                new Field(Messages.customer_customerID, Customer_.customerID),
                new Field(Messages.customer_companyName, Customer_.companyName),
                new Field(Messages.customer_contactName, Customer_.contactName),
                new Field(Messages.customer_address, Customer_.address),
                new Field(Messages.customer_postalCode, Customer_.postalCode),
                new Field(Messages.customer_city, Customer_.city));
    }

    @Override
    public MyQueryFactory<Customer> createQueryFactory() {
        return new MyQueryFactory<>(customerService::queryCustomer);
    }

    @Override
    public String getIdProperty() {
        return Customer_.customerID.getName();
    }

}
