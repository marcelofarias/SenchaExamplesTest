/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs.combination;

import com.sencha.examples.extjs.BaseExampleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ThemesTest extends BaseExampleTest {

    public ThemesTest(String browser) {
        super(browser);
    }
    
    @Override
    protected String getExamplePath() {
        return "/themes/index.html";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() throws Exception {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
        saveScreenshot("combination.Themes");
    }

}
