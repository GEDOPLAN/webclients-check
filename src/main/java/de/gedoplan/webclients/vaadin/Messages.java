/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin;

import com.vaadin.ui.UI;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author hjungnitsch
 */
public enum Messages {
    save,
    save_success,
    cancel,
    notallowed,
    menu_customer,
    menu_orders,
    menu_customerdetails,
    login_title,
    login_submit,
    login_name,
    login_password,
    login_invalid,
    logout,
    customer_customerID,
    customer_companyName,
    customer_contactName,
    customer_contactTitle,
    customer_address,
    customer_city,
    customer_region,
    customer_postalCode,
    customer_country,
    customer_phone,
    customer_fax,
    customer_discount,
    order_orderID,
    order_orderDate,
    order_requiredDate,
    order_shippedDate,
    order_shipCountry,
    order_shipVia,
    order_freight,
    order_shipName,
    order_shipAddress,
    order_shipCity,
    order_shipRegion,
    order_shipPostalCode;

    public String value() {
        return value(getLocale());
    }

    public String value(Locale locale) {
        return getBundle(locale).getString(name());
    }

    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("de.gedoplan.resources.messages", locale);
    }

    public Locale getLocale() {
        return UI.getCurrent().getLocale();
    }
}
