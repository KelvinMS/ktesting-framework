package core.assertions;

import com.beust.jcommander.internal.Lists;
import core.ScreenshotManager;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import java.util.List;

public class CustomAssertion extends Assertion {

    private static List<String> assert_messages = Lists.newArrayList();
    public static final ThreadLocal<WebDriver> drivers = new ThreadLocal();
    private WebDriver webDriver;

    public void getWebDriverAssert(ThreadLocal<WebDriver> driver) {
        drivers.set(driver.get());
    }

    @Override
    public void onBeforeAssert(IAssert a) {
        assert_messages.clear();
        //new ScreenshotManager().takeScreenShot(drivers.get());
        new ScreenshotManager().takeScreenshotToReport(drivers,assert_messages.toString(),ScreenshotManager.ScreenshotStatus.PASS );
    }

    @Override
    public void onAfterAssert(IAssert a) {
        //assert_messages.add("AfterAssert:" + a.getMessage());
    }

    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        StringBuilder assertDescription = new StringBuilder();
        assertDescription.append("Pass:<br /> expected ["+assertCommand.getExpected()+"]<br /> actual ["+assertCommand.getActual()+"]");
        if (assertCommand.getMessage() != null) {
            assertDescription.append(assertCommand.getMessage().isEmpty() ? "" : "<br /><pre>" + assertCommand.getMessage()).append("</pre>");
        }
        assert_messages.add(assertDescription.toString());
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        assert_messages.add("OnlyOnAssertFailure:" + assertCommand.getMessage());

    }

    public static List<String> getAssertMessages() {
        return assert_messages;
    }

}
