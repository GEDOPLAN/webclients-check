/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gedoplan.webclients.vaadin.base;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import com.vaadin.data.util.converter.StringToDateConverter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author hjungnitsch
 */
public class DemoConverterFactory extends DefaultConverterFactory {

    @Override
    protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(
            Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
        if (presentationType == String.class && modelType == Date.class) {
            return (Converter<PRESENTATION, MODEL>) new DateConverter();
        }
        return super.findConverter(presentationType, modelType);
    }

    public class DateConverter extends StringToDateConverter {

        @Override
        protected DateFormat getFormat(Locale locale) {
            return new SimpleDateFormat("dd.MM.yyyy");
        }
    }
}
