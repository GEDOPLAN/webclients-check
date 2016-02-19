package de.gedoplan.jaxgui.service;

import de.gedoplan.jaxgui.model.Order;
import de.gedoplan.jaxgui.model.User;
import de.gedoplan.jaxgui.model.User.UserRole;
import de.gedoplan.jaxgui.model.dto.QueryResult;
import de.gedoplan.jaxgui.model.dto.QuerySettings;
import de.gedoplan.jaxgui.repository.OrderRepository;
import de.gedoplan.jaxgui.system.CurrentUser;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @CurrentUser
    User currentUser;

    @RolesAllowed({UserRole.ADMINS, UserRole.CUSTOMERS})
    public QueryResult<Order> queryOrders(QuerySettings settings) {
        QueryResult orders;
        if (currentUser.isInRole(UserRole.ADMIN)) {
            orders = orderRepository.queryOrders(settings);
        } else {
            orders = orderRepository.queryCustomerOrders(currentUser.getCustomerID(), settings);
        }

        return orders;
    }

    @RolesAllowed({UserRole.ADMINS})
    public Order getOrder(Integer orderId) {
        Order order;
        if (currentUser.isInRole(UserRole.ADMIN)) {
            order = orderRepository.findOrder(orderId);
        } else {
            order = orderRepository.findCustomerOrder(currentUser.getCustomerID(), orderId);
        }

        return order;
    }
}
