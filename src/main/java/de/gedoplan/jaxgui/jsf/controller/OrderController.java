package de.gedoplan.jaxgui.jsf.controller;

import de.gedoplan.jaxgui.model.Order;
import de.gedoplan.jaxgui.model.dto.QueryResult;
import de.gedoplan.jaxgui.model.dto.QuerySettings;
import de.gedoplan.jaxgui.service.OrderService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class OrderController extends BaseTableController<Order> implements BaseController, Serializable {

    @Inject
    private OrderService customerService;

    @Override
    public QueryResult<Order> loadData(Integer pageSize, Integer pageStart, String[] sortAttributes, String[] sortDirections) {
        System.out.println("de.gedoplan.jaxgui.jsf.controller.OrderController.loadData()");
        QuerySettings querySettings = new QuerySettings(pageStart, pageSize, sortAttributes, sortDirections);
        QueryResult<Order> queryResult = customerService.queryOrders(querySettings);

        return queryResult;
    }

    @Override
    public Object getRowKey(Order customer) {
        return customer.getOrderID();
    }
}
