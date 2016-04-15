/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin;

import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServletService;
import de.gedoplan.webclients.vaadin.base.DemoConverterFactory;
import de.gedoplan.webclients.vaadin.base.ErrorHandler;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author hjungnitsch
 */
@WebServlet(urlPatterns = {"/vaadin/*", "/VAADIN/*"}, initParams = {
    @WebInitParam(name = "productionMode", value = "true"),
    @WebInitParam(name = "resourceCacheTime", value = "3600"),
    @WebInitParam(name = "widgetset", value = "demowidgets"), 
//Einkommentieren für Testen mit JMeter
//     @WebInitParam(name = "disable-xsrf-protection", value = "true"),
//    @WebInitParam(name = "syncIdCheck", value = "false")
})
public class DemoServlet extends VaadinCDIServlet {

    @Override
    protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws ServiceException {
        VaadinServletService service = super.createServletService(deploymentConfiguration);
        service.addSessionInitListener(e -> {
            e.getSession().setErrorHandler(new ErrorHandler());
            e.getSession().setConverterFactory(new DemoConverterFactory());
        });
        return service;
    }

}
