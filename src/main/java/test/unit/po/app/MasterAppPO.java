package test.unit.po.app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MasterAppPO {

    final AppiumDriver<MobileElement> driver;
    public WebDriverWait wait;


    public MasterAppPO(AppiumDriver<MobileElement> meuDriver) {
        driver = meuDriver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Utiliza o tempo do wait
     *
     * @return Retorna o objeto wait
     */
    public WebDriverWait getWait() {
        return wait;
    }


}
