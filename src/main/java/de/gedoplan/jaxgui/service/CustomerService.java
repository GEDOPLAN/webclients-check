package de.gedoplan.jaxgui.service;

import de.gedoplan.jaxgui.model.Customer;
import de.gedoplan.jaxgui.model.User;
import de.gedoplan.jaxgui.model.dto.CustomerOrderInformation;
import de.gedoplan.jaxgui.model.dto.QueryResult;
import de.gedoplan.jaxgui.model.dto.QuerySettings;
import de.gedoplan.jaxgui.repository.CustomerRepository;
import de.gedoplan.jaxgui.system.CurrentUser;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CustomerService {

    @Inject
    private CustomerRepository customerRepostory;

    @Inject
    @CurrentUser
    private User user;

    @RolesAllowed({User.UserRole.ADMINS})
    public QueryResult<Customer> queryCustomer(QuerySettings settings) {
        return customerRepostory.queryCustomer(settings);
    }

    @RolesAllowed({User.UserRole.ADMINS, User.UserRole.CUSTOMERS})
    public Customer getCustomerById(String customerID) {
        validateCustomerAccess(customerID);
        return customerRepostory.getCustomerByID(customerID);
    }

    @RolesAllowed({User.UserRole.ADMINS, User.UserRole.CUSTOMERS})
    public Customer updateCustomer(Customer customer) {
        validateCustomerAccess(customer.getCustomerID());
        return customerRepostory.updateCustomer(customer);
    }

    @RolesAllowed({User.UserRole.ADMINS, User.UserRole.CUSTOMERS})
    public CustomerOrderInformation calculateCustomerDiscount(String customerId) {
        validateCustomerAccess(customerId);
        CustomerOrderInformation orderInformation = customerRepostory.getCustomerOrderInfomration(customerId);
        Double totalAmount = orderInformation.getTotalAmount();
        Double discount = 0.;

        if (totalAmount > 10000) {
            discount = 5.;
        } else if (totalAmount > 5000) {
            discount = 3.;
        } else if (totalAmount > 1000) {
            discount = 2.5;
        }

        orderInformation.setDiscount(discount);

        return orderInformation;
    }

    private void validateCustomerAccess(String customerId) {
        if (!user.isInRole(User.UserRole.ADMIN) && !user.getCustomerID().equals(customerId)) {
            throw new EJBAccessException();
        }
    }
}
