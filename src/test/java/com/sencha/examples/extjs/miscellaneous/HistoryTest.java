/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs.miscellaneous;

import com.saucelabs.junit.ConcurrentParameterized;
import com.sencha.examples.extjs.BaseExampleTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(ConcurrentParameterized.class)
public class HistoryTest extends BaseExampleTest {

    public HistoryTest(String platform, String browser, String version, String theme) {
        super(platform, browser, version, theme);
    }
    
    @Override
    protected String getExamplePath() {
        return "/history/history.html";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() throws Exception {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
//        saveScreenshot("grids.XMLGrid");
    }

}