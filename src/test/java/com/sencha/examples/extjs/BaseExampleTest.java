/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs;

import com.sencha.PropertiesManager;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

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
        String webDriverHub = _propertiesManager.getProperty("webdriver.hub");
        return new URL(String.format("http://%s:4444/wd/hub", webDriverHub));
    }

    protected WebDriver getWebDriver(String browserName) throws Exception {
        WebDriver webDriver = null;
        if ("chrome".equals(browserName)) {
            webDriver = new RemoteWebDriver(
                    getWebDriverHubURL(),
                    DesiredCapabilities.chrome());
        } else if ("firefox".equals(browserName)) {
            webDriver = new RemoteWebDriver(
                    getWebDriverHubURL(),
                    DesiredCapabilities.firefox());
        } else if ("ie".equals(browserName)){
            webDriver = new RemoteWebDriver(
                    getWebDriverHubURL(),
                    DesiredCapabilities.internetExplorer());
        }
        
        if (webDriver != null) {
            return new Augmenter().augment(webDriver);
        }
        
        throw new RuntimeException(String.format("Unsupported browser %s", browserName));
    }

    protected List<String> getJavaScriptErrors() {
        return (List<String>) getJavascriptExecutor().executeScript("return window.__webdriver_javascript_errors");
    }
    
    protected WebDriver getWebDriver() {
        return _webDriver;
    }
    
    protected TakesScreenshot getScreenshotTaker() {
        return (TakesScreenshot) _webDriver;
    }
    
    protected JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) _webDriver;
    }
    
    protected void saveScreenshot(String name) throws IOException {
        byte[] screenshot = getScreenshotTaker().getScreenshotAs(OutputType.BYTES);
        FileOutputStream output = new FileOutputStream(new File(
                _propertiesManager.getProperty("screenshots.dir"),
                String.format("%s-%s.png", name, _browser)));
        IOUtils.write(screenshot, output);
        output.close();
    }

    protected abstract String getExamplePath();

    @Before
    public void openExemple() throws Exception {
        String callbackAddress = _propertiesManager.getProperty("callback.address");
        _webDriver = getWebDriver(_browser);
        _webDriver.manage().window().maximize();
        _webDriver.get(String.format(
                "http://%s:1841/ext/build/examples%s",
                callbackAddress,
                getExamplePath()));
        
        long startTime = System.currentTimeMillis();
        boolean extIsReady = false;
        while (!extIsReady && System.currentTimeMillis() < startTime + 10000) {
            Object extReadyState = getJavascriptExecutor().executeScript(
                    "return window.Ext && window.Ext.isReady;");
            if (Boolean.TRUE.equals(extReadyState)) {
                extIsReady = true;
            }
        }
        
        assertThat(extIsReady).isTrue();

        Thread.sleep(5000);
    }

    @After
    public void closeExample() throws Exception {
        _webDriver.close();
    }

    private String _browser;
    private WebDriver _webDriver;
    private PropertiesManager _propertiesManager = PropertiesManager.getInstance();
    
}
