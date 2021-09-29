package test.unit.po.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MemberPO extends MasterPO{
    /**
     * Set default timeouts
     * Initialization of Elements
     *
     * @param driver Passed by PageObjects
     */
    public MemberPO(ThreadLocal<WebDriver> driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[@class='page-title'][contains(text(),'Membros')]")
    public WebElement lblMembros;
}
