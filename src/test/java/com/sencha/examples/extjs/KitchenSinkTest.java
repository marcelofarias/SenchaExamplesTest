/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class KitchenSinkTest extends BaseExampleTest {

    public KitchenSinkTest(String browser) {
        super(browser);
    }
    
    @Override
    protected String getExamplePath() {
        return "/kitchensink";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() {
        List<String> errors = getJavaScriptErrors();
        Assert.assertTrue(errors.isEmpty());
    }

}
