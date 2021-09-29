package test.unit.po.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePO extends MasterPO{


    /**
     * Set default timeouts
     * Initialization of Elements
     *
     * @param driver Passed by PageObjects
     */
    public HomePO(ThreadLocal<WebDriver> driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[contains(text(),'Livro em destaque')]")
    public WebElement lblLivroEmDestaque;

}
