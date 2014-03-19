/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs.combination;

import com.saucelabs.junit.Parallelized;
import com.sencha.examples.extjs.BaseExampleTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parallelized.class)
public class KitchenSinkTest extends BaseExampleTest {

    public KitchenSinkTest(String browser) {
        super(browser);
    }
    
    @Override
    protected String getExamplePath() {
        return "/kitchensink";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() throws Exception {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
        saveScreenshot("combination.KitchenSink");
    }

}
