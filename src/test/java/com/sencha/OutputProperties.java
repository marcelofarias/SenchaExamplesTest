/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha;

import org.junit.Test;

public class OutputProperties {
    
    @Test
    public void outputProperties() {
        System.out.println("SELENIUM_HOST");
        System.out.println(readPropertyOrEnv("SELENIUM_HOST", null));
        
        System.out.println("SELENIUM_PORT");
        System.out.println(readPropertyOrEnv("SELENIUM_PORT", null));
        
        System.out.println("SELENIUM_PLATFORM");
        System.out.println(readPropertyOrEnv("SELENIUM_PLATFORM", null));
        
        System.out.println("SELENIUM_VERSION");
        System.out.println(readPropertyOrEnv("SELENIUM_VERSION", null));
        
        System.out.println("SELENIUM_BROWSER");
        System.out.println(readPropertyOrEnv("SELENIUM_BROWSER", null));
        
        System.out.println("SELENIUM_DRIVER");
        System.out.println(readPropertyOrEnv("SELENIUM_DRIVER", null));
        
        System.out.println("SAUCE_ONDEMAND_BROWSERS");
        System.out.println(readPropertyOrEnv("SAUCE_ONDEMAND_BROWSERS", null));
        
        System.out.println("SELENIUM_URL");
        System.out.println(readPropertyOrEnv("SELENIUM_URL", null));
        
        System.out.println("SAUCE_USER_NAME");
        System.out.println(readPropertyOrEnv("SAUCE_USER_NAME", null));
        
        System.out.println("SAUCE_API_KEY");
        System.out.println(readPropertyOrEnv("SAUCE_API_KEY", null));
    }
    
    private static String readPropertyOrEnv(String key, String defaultValue) {
        String v = System.getProperty(key);
        if (v == null)
            v = System.getenv(key);
        if (v == null)
            v = defaultValue;
        return v;
    }
    
}
