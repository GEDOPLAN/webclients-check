package de.gedoplan.webclients.vaadin.demowidgets;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.client.ui.formlayout.FormLayoutConnector;
import com.vaadin.client.ui.gridlayout.GridLayoutConnector;
import com.vaadin.client.ui.label.LabelConnector;
import com.vaadin.client.ui.link.LinkConnector;
import com.vaadin.client.ui.menubar.MenuBarConnector;
import com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector;
import com.vaadin.client.ui.orderedlayout.VerticalLayoutConnector;
import com.vaadin.client.ui.panel.PanelConnector;
import com.vaadin.client.ui.passwordfield.PasswordFieldConnector;
import com.vaadin.client.ui.table.TableConnector;
import com.vaadin.client.ui.textfield.TextFieldConnector;
import com.vaadin.client.ui.ui.UIConnector;
import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;
import com.vaadin.shared.ui.Connect.LoadStyle;
import java.util.HashSet;
import java.util.Set;

public class OptimizedConnectorBundleLoaderFactory extends
        ConnectorBundleLoaderFactory {

    private Set<String> eagerConnectors = new HashSet<String>();

    {
        eagerConnectors.add(UIConnector.class.getName());
        eagerConnectors.add(VerticalLayoutConnector.class.getName());
        eagerConnectors.add(MenuBarConnector.class.getName());
        eagerConnectors.add(LabelConnector.class.getName());
        eagerConnectors.add(ButtonConnector.class.getName());
        eagerConnectors.add(PanelConnector.class.getName());
        eagerConnectors.add(HorizontalLayoutConnector.class.getName());
        eagerConnectors.add(GridLayoutConnector.class.getName());
        eagerConnectors.add(TextFieldConnector.class.getName());
        eagerConnectors.add(TableConnector.class.getName());
        eagerConnectors.add(LinkConnector.class.getName());
        eagerConnectors.add(FormLayoutConnector.class.getName());
        eagerConnectors.add(PasswordFieldConnector.class.getName());
//        eagerConnectors.add(GridConnector.class.getName());
//        eagerConnectors.add(RpcDataSourceConnector.class.getName());
//        eagerConnectors.add(DetailComponentManagerConnector.class.getName());
//        eagerConnectors.add(TextRendererConnector.class.getName());
//        eagerConnectors.add(NoSelectionModelConnector.class.getName());
//        eagerConnectors.add(ButtonRendererConnector.class.getName());
    }

    @Override
    protected LoadStyle getLoadStyle(JClassType connectorType) {
        if (eagerConnectors.contains(connectorType.getQualifiedBinaryName())) {
            return LoadStyle.EAGER;
        } else {
            return LoadStyle.LAZY;
        }
    }
}
