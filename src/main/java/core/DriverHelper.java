package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Paths;

public class DriverHelper {

    private static final String PROPERTYCHROME = "webdriver.chrome.driver";
    private static final String PROPERTYFIREFOX = "webdriver.gecko.driver";
    private static final String GECKODRIVER_PATH = "" + Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "drivers", "geckodriver");
    private static final String CHROMEDRIVER_PATH = "" + Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "drivers", "chromedriver");

    public WebDriver setupDriver(String browser) {
        browser = browser.toLowerCase();
        WebDriver driver;
        setupDriverPaths();
        DesiredCapabilities des = new DesiredCapabilities();
        des.setBrowserName(browser);
        if (browser.contains("chrome")) {
            driver = new ChromeDriver();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null"); //Remove checkodriver log
            des.setCapability("marionette", true);

            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        return driver;
    }

    public void setupDriverPaths() {
        System.setProperty(PROPERTYFIREFOX, GECKODRIVER_PATH);
        System.setProperty(PROPERTYCHROME, CHROMEDRIVER_PATH);

    }


    public static DesiredCapabilities getCaps(String udid,String deviceName,String appPackage,String appActivity,String bundleId,String platformName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        if (platformName.equalsIgnoreCase("ios")) {
            caps.setCapability("bundleId", bundleId);
            caps.setCapability("automationName", CapabilitiesHelper.Platform.AUTOMATION_NAME_IOS);
            /* caps.setCapability("autoAcceptAlerts",true); */
            caps.setCapability("platformVersion","11.0.2");
        } else {
            caps.setCapability("appPackage", appPackage);
            caps.setCapability("appActivity", appActivity);
            caps.setCapability("automationName", CapabilitiesHelper.Platform.AUTOMATION_NAME_ANDROID);
        }
        caps.setCapability("udid", udid);
        /*caps.setCapability("wdaEventloopIdleDelay",3);*/
        caps.setCapability("platformName", platformName);
        caps.setCapability("autoGrantPermissions",true);
        caps.setCapability("deviceName", deviceName);
        return caps;
    }


    public <T extends AppiumDriver<?>> T getNewAppDriver(DesiredCapabilities capabilities) {
        if (capabilities.getPlatform().is(Platform.ANDROID)) {
            return (T) this.getNewAndroidAppDriver(capabilities);
        }else {
            return (T) this.getNewIOSAppDriver(capabilities);
        }
    }

    public <T extends WebElement> AndroidDriver<T> getNewAndroidAppDriver(DesiredCapabilities capabilities) {
        return new AndroidDriver(capabilities);
    }

    public IOSDriver getNewIOSAppDriver(DesiredCapabilities capabilities) {
        return new IOSDriver(capabilities);
    }
}

