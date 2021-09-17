package test.unit.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MasterPO {
    final ThreadLocal<WebDriver> driver;
    final WebDriverWait wait;

    /**
     * Set default timeouts
     * Initialization of Elements
     * @param driver Passed by PageObjects
     */
    public MasterPO(ThreadLocal<WebDriver> driver){
        this.driver = driver;
        this.driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(this.driver.get(), this);
        wait = new WebDriverWait(this.driver.get(), 5);
    }

    /**
     * Set Glabally WebDriverWait
     * @return Retorna um WebDriverWait Padrao
     */
    public WebDriverWait getWait(){
        return wait;
    }

}
