/*
 * Copyright (c) 2012-2013. Sencha Inc.
 */
package com.sencha.examples.extjs.grids;

import com.sencha.examples.extjs.BaseExampleTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class LockingGroupSummaryGroupHeadersTest extends BaseExampleTest {

    public LockingGroupSummaryGroupHeadersTest(String browser) {
        super(browser);
    }
    
    @Override
    protected String getExamplePath() {
        return "/grid/locking-grp-summary-grp-hdrs-grid.html";
    }
    
    @Test
    public void exampleLoadsWithoutErrors() throws Exception {
        List<String> errors = getJavaScriptErrors();
        assertThat(errors).isEmpty();
        saveScreenshot("grids.LockingGroupSummaryGroupHeaders");
    }

}
