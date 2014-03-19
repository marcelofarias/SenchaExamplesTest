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
public class LegacyChartsKitchenSinkTest extends BaseExampleTest {

    public LegacyChartsKitchenSinkTest(String browser) {
        super(browser);
    }
    
    @Override
    protected String getExamplePath() {
        return "/charts-kitchensink/#basic-column";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() throws Exception {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
        saveScreenshot("combination.LegacyChartsKitchenSink");
    }

}