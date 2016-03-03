/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.base;

import com.vaadin.ui.Field;
import de.gedoplan.webclients.model.Customer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotNull;
import org.vaadin.viritin.MBeanFieldGroup;

/**
 *
 * @author hjungnitsch
 */
public class DemoFieldGroup<T> extends MBeanFieldGroup<T> {

    public DemoFieldGroup(Class beanType) {
        super(beanType);
    }

    public void configureNotNull() {
        for (Object property : getBoundPropertyIds()) {
            final Field<?> field = getField(property);
            try {
                java.lang.reflect.Field declaredField = findDeclaredField(property, nonHiddenBeanType);
                final NotNull notNullAnnotation = declaredField.getAnnotation(NotNull.class);
                if (notNullAnnotation != null) {
                    field.setRequired(true);
                    // Hack, keine vernünftige Lokalisierung, da müsste eigentlich mit dem MessageInterpolator gearbeitet werden
                    Set<ConstraintViolation<Customer>> violations = Validation.buildDefaultValidatorFactory().getValidator().validateValue(nonHiddenBeanType, property.toString(), null);
                    field.setRequiredError(violations.stream().filter(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class)).findFirst().get().getMessage());
                }
            } catch (NoSuchFieldException | SecurityException ex) {
                Logger.getLogger(MBeanFieldGroup.class.getName()).log(Level.FINE, null, ex);
            }
        }
    }
}
