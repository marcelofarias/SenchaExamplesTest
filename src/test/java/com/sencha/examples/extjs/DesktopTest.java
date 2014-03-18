/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class DesktopTest extends BaseExampleTest {

    public DesktopTest(String browser) {
        super(browser);
    }
    
    @Override
    protected String getExamplePath() {
        return "/desktop";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
    }

}
