/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    
    public static PropertiesManager getInstance() {
        if (_instance == null) {
            _instance = new PropertiesManager(); 
        }
        return _instance;
    }
    
    public String getProperty(String key) {
        return getProperties().getProperty(key, System.getProperty(key));
    }
    
    private Properties getProperties() {
        if (_properties == null) {
            _properties = new Properties();
            try {
                InputStream in = getClass().getResourceAsStream("/local.properties");
                _properties.load(in);
                in.close();
            } catch (Exception e) { 
                // ignore
            }
        }
        return _properties;
    }
    
    private static PropertiesManager _instance;
    private Properties _properties;
    
}
