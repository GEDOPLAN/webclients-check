/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Table;
import de.gedoplan.webclients.vaadin.Messages;
import de.gedoplan.webclients.vaadin.base.BaseView;
import de.gedoplan.webclients.vaadin.base.MyQueryFactory;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.metamodel.SingularAttribute;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.LazyQueryDefinition;

/**
 *
 * @author hjungnitsch
 */
public abstract class BaseMasterView<E> extends BaseView {

    protected Table table;

    @Override
    public void myenter(ViewChangeListener.ViewChangeEvent event) {
        if (getComponentCount() > 0) {
            return;
        }
        LazyQueryDefinition queryDefinition = new LazyQueryDefinition(false, 25, getIdProperty());
        queryDefinition.setMaxNestedPropertyDepth(3);
        LazyQueryContainer container = new LazyQueryContainer(queryDefinition, createQueryFactory());
        container.getQueryView().setMaxCacheSize(100);
        table = new Table(null, container);
        table.setWidth(100, Unit.PERCENTAGE);
        table.setPageLength(10);
        table.setStyleName("masterview");
        getColumns().forEach((field) -> {
            container.addContainerProperty(field.getPropertyId(), field.getType(), null, true, true);
            table.setColumnHeader(field.getPropertyId(), field.title.value());
        });
        table.setVisibleColumns((Object[]) getColumns().stream().map(f -> f.getPropertyId()).toArray(size -> new String[size]));
        setMargin(true);
        setSizeFull();
        addComponent(table);
        setComponentAlignment(table, Alignment.TOP_CENTER);
        configureTable();
    }

    public abstract List<Field> getColumns();

    public abstract MyQueryFactory<E> createQueryFactory();

    public abstract String getIdProperty();

    protected void configureTable() {

    }

    protected class Field {

        protected final SingularAttribute<?, ?>[] attributes;
        protected final Messages title;

        public Field(Messages title, SingularAttribute<?, ?>... attributes) {
            this.attributes = attributes;
            this.title = title;
        }

        protected String getPropertyId() {
            return Arrays.stream(attributes).map(a -> a.getName()).collect(Collectors.joining("."));
        }

        public Class<?> getType() {
            return attributes[attributes.length - 1].getBindableJavaType();
        }

    }
}
