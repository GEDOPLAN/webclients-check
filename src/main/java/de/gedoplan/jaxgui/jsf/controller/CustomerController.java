package de.gedoplan.jaxgui.jsf.controller;

import de.gedoplan.jaxgui.model.Customer;
import de.gedoplan.jaxgui.model.dto.QueryResult;
import de.gedoplan.jaxgui.model.dto.QuerySettings;
import de.gedoplan.jaxgui.service.CustomerService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CustomerController extends BaseTableController<Customer> implements BaseController, Serializable {

    @Inject
    private CustomerService customerService;

    @Override
    public QueryResult<Customer> loadData(Integer pageSize, Integer pageStart, String[] sortAttributes, String[] sortDirections) {
        QuerySettings querySettings = new QuerySettings(pageStart, pageSize, sortAttributes, sortDirections);
        QueryResult<Customer> queryResult = customerService.queryCustomer(querySettings);

        return queryResult;
    }

    @Override
    public Object getRowKey(Customer customer) {
        return customer.getCustomerID();
    }
}
