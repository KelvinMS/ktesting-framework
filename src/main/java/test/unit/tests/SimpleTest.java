package test.unit.tests;

import core.ExtentTestListeners;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListeners.class)
public class SimpleTest {

    @Test
    private void sampleTestA(){

        Assert.assertTrue(false);
    }

    @Test
    private void sampleTestB(){

        Assert.assertTrue(true);
    }
}
