/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs;

import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public abstract class BaseExampleTest {

    public BaseExampleTest(String browser) {
        _browser = browser;
    }

    @Parameterized.Parameters(name="{0}")
    public static List data() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"firefox"},
                {"ie"}
        });
    }
    
    private URL getWebDriverHubURL() throws Exception {
        String webDriverHub = System.getProperty("webdriver.hub");
        return new URL(String.format("http://%s:4444/wd/hub", webDriverHub));
    }

    protected RemoteWebDriver getWebDriver(String browserName) throws Exception {
        if ("chrome".equals(browserName)) {
            return new RemoteWebDriver(
                    getWebDriverHubURL(),
                    DesiredCapabilities.chrome());
        } else if ("firefox".equals(browserName)) {
            return new RemoteWebDriver(
                    getWebDriverHubURL(),
                    DesiredCapabilities.firefox());
        } else if ("ie".equals(browserName)){
            return new RemoteWebDriver(
                    getWebDriverHubURL(),
                    DesiredCapabilities.internetExplorer());
        }
        throw new RuntimeException(String.format("Unsupported browser %s", browserName));
    }

    protected List<String> getJavaScriptErrors() {
        return (List<String>) getWebDriver().executeScript("return window.__webdriver_javascript_errors");
    }
    
    protected RemoteWebDriver getWebDriver() {
        return _webDriver;
    }

    protected abstract String getExamplePath();

    @Before
    public void openExemple() throws Exception {
        String callbackAddress = System.getProperty("callback.address");
        _webDriver = getWebDriver(_browser);
        _webDriver.get(String.format(
                "http://%s:1841/ext/build/examples%s",
                callbackAddress,
                getExamplePath()));
    }

    @After
    public void closeExample() throws Exception {
        _webDriver.close();
    }

    private String _browser;
    private RemoteWebDriver _webDriver;
    
}
