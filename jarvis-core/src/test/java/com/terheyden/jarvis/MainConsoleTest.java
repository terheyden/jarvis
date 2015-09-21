package com.terheyden.jarvis;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Unit tests for MainConsole.
 */
public class MainConsoleTest
{
    /**
     * Do setup ONCE before ANY test runs.
     */
    @BeforeClass
    public void setup() {
    }

    /**
     * Do teardown after ALL tests have run.
     */
    @AfterClass
    public void teardown() {
    }

    /**
     * Do setup before EVERY test run.
     */
    @BeforeMethod
    public void beforeEveryTest() {
    }

    /**
     * Do teardown after EVERY test run.
     */
    @AfterMethod
    public void afterEveryTest() {
    }

    /**
     * Simple test.
     */
    @Test
    public void test1() {
        assertTrue(2 + 2 == 4);
    }

    @Test
    public void test2() {
        Assert.assertNotNull("Hey now!");
    }
}
