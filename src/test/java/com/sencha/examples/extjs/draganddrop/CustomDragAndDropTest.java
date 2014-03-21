/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs.draganddrop;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.sencha.examples.extjs.BaseExampleTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(ConcurrentParameterized.class)
public class CustomDragAndDropTest extends BaseExampleTest {

    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
    
    public CustomDragAndDropTest(String platform, String browser, String version, String theme) {
        super(platform, browser, version, theme);
    }
    
    @Override
    protected String getExamplePath() {
        return "/dd/dragdropzones.html";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() throws Exception {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
//        saveScreenshot("combination.Ticket");
    }

}
