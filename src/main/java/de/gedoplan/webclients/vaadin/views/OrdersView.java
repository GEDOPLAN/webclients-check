/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import com.vaadin.cdi.CDIView;
import de.gedoplan.webclients.model.Order;
import de.gedoplan.webclients.model.Order_;
import de.gedoplan.webclients.service.OrderService;
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
@CDIView(Konstanten.VAADIN_VIEW_ORDERS)
public class OrdersView extends BaseMasterView<Order> {

    @Inject
    private OrderService orderService;

    @Override
    public List<Field> getColumns() {
        return Arrays.asList(new Field(Messages.order_orderID, Order_.orderID),
                new Field(Messages.order_orderDate, Order_.orderDate),
                new Field(Messages.order_freight, Order_.freight),
                new Field(Messages.order_shipName, Order_.shipName),
                new Field(Messages.order_shipAddress, Order_.shipAddress),
                new Field(Messages.order_shipPostalCode, Order_.shipPostalCode),
                new Field(Messages.order_shipCity, Order_.shipCity));
    }

    @Override
    public MyQueryFactory<Order> createQueryFactory() {
        return new MyQueryFactory<>(orderService::queryOrders);
    }

    @Override
    public String getIdProperty() {
        return Order_.orderID.getName();
    }

}
