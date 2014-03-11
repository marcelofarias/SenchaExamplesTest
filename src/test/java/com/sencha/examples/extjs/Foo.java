/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs;

import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Foo {
    
    @Test
    public void foo() throws Exception {
        RemoteWebDriver webDriver = new RemoteWebDriver(
                new URL("http://localhost:4444/wd/hub"),
                DesiredCapabilities.chrome());
        webDriver.get("http://localhost:1841/build/examples/desktop");
        
        boolean extPresent = false;
        while (!extPresent) {
            Object windowExt = webDriver.executeScript("return Ext.isReady;");
            System.out.println(windowExt);
            extPresent = windowExt != null;
            if (extPresent) {
                break;
            } else {
                Thread.sleep(1000);
            }
        }
        System.out.println(webDriver.executeScript("return Ext.isReady;"));
        webDriver.close();
    }
    
}
