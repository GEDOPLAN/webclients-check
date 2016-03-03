/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.base;

import com.vaadin.data.Item;
import de.gedoplan.webclients.model.dto.QueryResult;
import de.gedoplan.webclients.model.dto.QuerySettings;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.vaadin.addons.lazyquerycontainer.NestingBeanItem;
import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

/**
 *
 * @author hjungnitsch
 */
public class MyQueryFactory<T> implements QueryFactory {

    private Function<QuerySettings, QueryResult<T>> queryFunction;

    public MyQueryFactory(Function<QuerySettings, QueryResult<T>> queryFunction) {
        this.queryFunction = queryFunction;
    }

    @Override
    public Query constructQuery(QueryDefinition qd) {
        return new MyQuery(qd);
    }

    public class MyQuery implements Query {

        private QueryDefinition qd;
        private QueryResult<T> queryResult;

        public MyQuery(QueryDefinition qd) {
            this.qd = qd;
        }

        @Override
        public int size() {
            return queryResult != null ? queryResult.getResultCount().intValue() : queryFunction.apply(new QuerySettings(1, 0)).getResultCount().intValue();
        }

        @Override
        public List<Item> loadItems(int i, int i1) {
            Object[] sortPropertyIds = (qd.getSortPropertyIds().length == 0) ? qd.getDefaultSortPropertyIds() : qd.getSortPropertyIds();
            boolean[] sortPropertyAscendingStates = (qd.getSortPropertyIds().length == 0) ? qd.getDefaultSortPropertyAscendingStates() : qd.getSortPropertyAscendingStates();
            String[] ascending = new String[sortPropertyAscendingStates.length];
            int index = 0;
            for (boolean asc : sortPropertyAscendingStates) {
                ascending[index++] = asc ? "asc" : "desc";
            }
            queryResult = queryFunction.apply(new QuerySettings(i, i1,
                    Arrays.stream(sortPropertyIds).map(id -> id.toString()).toArray(size -> new String[size]),
                    ascending));
            return queryResult.getResult().stream().map(e -> new NestingBeanItem<>(e, qd.getMaxNestedPropertyDepth(), qd.getPropertyIds())).collect(Collectors.toList());
        }

        @Override
        public void saveItems(List<Item> list, List<Item> list1, List<Item> list2) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean deleteAllItems() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public Item constructItem() {
            throw new UnsupportedOperationException("Not supported.");
        }

    }
}
