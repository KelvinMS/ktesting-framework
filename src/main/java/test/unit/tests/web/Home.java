package test.unit.tests.web;

import core.DriverHelper;
import core.ExtentTestListeners;
import core.assertions.CustomAssertion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import test.unit.po.web.HomePO;
import test.unit.po.web.MasterPO;
import test.unit.po.web.MemberPO;

@Listeners(ExtentTestListeners.class)
public class Home {


    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private WebDriverWait wait;
    private CustomAssertion assertion;

    private void setup(String browser) {
        DriverHelper driverHelper = new DriverHelper();
        driver.set(driverHelper.setupDriver(browser));
        assertion = new CustomAssertion();
        assertion.getWebDriverAssert(driver);
        wait = new MasterPO(driver).getWait();
    }

    @Parameters({"browser", "url", "user", "senha"})
    @Test()
    private void acessarHome(@Optional("chrome") String browser,
                             @Optional("https://poesiafaclube.com/") String url,
                             @Optional("kelvin.moreira@westwing.com.br") String user,
                             @Optional("dado651b") String senha) {
        setup(browser);
        driver.get().get(url);
        HomePO homePO = new HomePO(driver);
        assertion.assertEquals(wait.until(ExpectedConditions.visibilityOf(homePO.lblLivroEmDestaque)).isDisplayed(), true, "Entry at Home page ");
    }

    @Parameters({"browser", "url", "user", "senha"})
    @Test()
    private void acessarMembros(@Optional("chrome") String browser,
                                @Optional("https://poesiafaclube.com/") String url,
                                @Optional("kelvin.moreira@westwing.com.br") String user,
                                @Optional("dado651b") String senha) {
        setup(browser);
        driver.get().get(url);
        MemberPO memberPO = new HomePO(driver).acessMemberArea();
        assertion.assertEquals(wait.until(ExpectedConditions.visibilityOf(memberPO.lblMembros)).isDisplayed(), false, "Entry at Member page ");
    }

    /**
     * Finish Driver instance
     */
    @AfterMethod
    private void tearDown() {
        driver.get().quit();
    }


}
