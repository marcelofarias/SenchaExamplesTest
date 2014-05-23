/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.sencha.PropertiesManager;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public abstract class BaseExampleTest implements SauceOnDemandSessionIdProvider {

    public BaseExampleTest(String platform, String browser, String version, String theme) {
        super();
        _platform = platform;
        _browser = browser;
        _version = version;
        _theme = theme;
    }

    private PropertiesManager _propertiesManager = PropertiesManager.getInstance();

    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
            _propertiesManager.getProperty("saucelabs.username"),
            _propertiesManager.getProperty("saucelabs.accesskey"));

    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @ConcurrentParameterized.Parameters(name = "{0}-{1}-{2}-{3}")
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"Windows 7", "chrome", "33", "neptune"});
        browsers.add(new String[]{"Windows 7", "firefox", "27", "neptune"});
        browsers.add(new String[]{"Windows 7", "internet explorer", "10", "neptune"});
        browsers.add(new String[]{"Windows 7", "internet explorer", "11", "neptune"});
        browsers.add(new String[]{"Windows 7", "internet explorer", "9", "neptune"});
//        browsers.add(new String[]{"Windows 7", "internet explorer", "8", "neptune"});
//        browsers.add(new String[]{"Windows 7", "opera", "12", "neptune"});
        browsers.add(new String[]{"OS X 10.8", "safari", "6", "neptune"});
        browsers.add(new String[]{"OS X 10.9", "safari", "7", "neptune"});
        browsers.add(new String[]{"mac", "iPad", "7", "neptune"});
//        browsers.add(new String[]{"linux", "android", "4.3", "neptune"});
        return browsers;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        capabilities.setCapability("command-timeout", 600);
        capabilities.setCapability("idle-timeout", 120);
        // capabilities.setCapability("record-video", false);
        capabilities.setCapability("record-screenshots", false);
        
        capabilities.setCapability(CapabilityType.PLATFORM, _platform);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, _browser);
        capabilities.setCapability(CapabilityType.VERSION, _version);
        
        if ("iPad".equals(_browser)) {
            capabilities.setCapability("device-orientation", "portrait");
        } else if ("android".equals(_browser)) {
            capabilities.setCapability("device-type", "tablet");
            capabilities.setCapability("device-orientation", "portrait");
        }
        
        
        capabilities.setCapability("name",
                String.format("%s (%s - %s - %s - %s)",
                        getExamplePath(),
                        _platform,
                        _browser,
                        _version,
                        _theme));

        for (int i = 0; i < 3; i++) {
            try {
                _driver = new RemoteWebDriver(
                        new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                        capabilities);
                break;
            } catch (Exception e) {
                if (i == 2) {
                    throw e;
                }
            }
        }
        
        _sessionId = (((RemoteWebDriver) _driver).getSessionId()).toString();
        _driver = new Augmenter().augment(_driver);
        
        openExample();
    }
    
    protected void openExample() throws Exception {
        String callbackAddress = _propertiesManager.getProperty("callback.address");
        String address = String.format(
                "http://%s:8888/ext/build/examples%s",
                callbackAddress,
                getExamplePath());
        
        if (address.contains("#")) {
            address = address.replace("#", String.format("?theme=%s#", _theme));
        } else {
            address = address.concat(String.format("?theme=%s#", _theme));
        }
        
        _driver.get(address);

        long startTime = System.currentTimeMillis();
        boolean extIsReady = false;
        while (!extIsReady && System.currentTimeMillis() < startTime + 120000) {
            Object extReadyState = getJavascriptExecutor().executeScript(
                    "return window.Ext && window.Ext.isReady;");
            if (Boolean.TRUE.equals(extReadyState)) {
                extIsReady = true;
            }
        }
        
        if (!extIsReady) {
            Assert.fail("ExtJS didn't get ready in 120 seconds. JS errors:\n" + getJavaScriptErrors());
        }
    }

    @After
    public void tearDown() throws Exception {
        _driver.quit();
    }

    @Override
    public String getSessionId() {
        return _sessionId;
    }

    protected List<String> getJavaScriptErrors() {
        return (List<String>) getJavascriptExecutor().executeScript("return window.__webdriver_javascript_errors");
    }
    
    protected WebDriver getDriver() {
        return _driver;
    }
    
    protected TakesScreenshot getScreenshotTaker() {
        return (TakesScreenshot) _driver;
    }
    
    protected JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) _driver;
    }
    
    protected void saveScreenshot(String name) throws IOException {
        byte[] screenshot = getScreenshotTaker().getScreenshotAs(OutputType.BYTES);
        FileOutputStream output = new FileOutputStream(new File(
                _propertiesManager.getProperty("screenshots.dir"),
                String.format("%s-%s-%s-%s-%s.png", name, _platform, _browser, _version, _theme)));
        IOUtils.write(screenshot, output);
        output.close();
    }

    protected abstract String getExamplePath();


    private String _platform;
    private String _browser;
    private String _version;
    private String _theme;
    
    private WebDriver _driver;
    private String _sessionId;
    
}
