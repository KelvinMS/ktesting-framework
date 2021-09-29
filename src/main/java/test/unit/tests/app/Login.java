package test.unit.tests.app;

import core.*;
import core.assertions.CustomAssertion;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import test.unit.po.app.LoginPO;
import test.unit.po.app.MasterAppPO;

import static org.testng.Assert.assertEquals;

@Listeners(ExtentTestListeners.class)
public class Login {


    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;
    private CustomAssertion assertion;

    /**
     * Realiza o Setup para inicializar conexao e abrir aplicativo
     *
     * @param udid         id do device no crashken a ser utilizado para executar, só é necessário quando executado via crashken!
     * @param appPackage   Package da aplicação a ser aberta (Appium capabilities)
     * @param appActivity  Activity a ser iniciada no aplicativo (Appium capabilities)
     * @param platformName Plataforma a ser executado: ex Android
     * @param bundleId     Identifica o ID de um aparelho Apple
     * @param deviceName   Nome de um aparelho
     */
    @BeforeMethod
    @Parameters({"deviceId", "deviceName", "appPackage", "appActivity", "bundleId", "platformName"})
    public void setUp(@Optional("3200ac97bca2a573") String udid,
                      @Optional("device") String deviceName,
                      @Optional(CapabilitiesHelper.Westwingclub.APP_PACKAGE_STAGE) String appPackage,
                      @Optional(CapabilitiesHelper.Westwingclub.MAIN_ACTIVITY) String appActivity,
                      @Optional(CapabilitiesHelper.Westwingclub.BUNDLE_ID) String bundleId,
                      @Optional(CapabilitiesHelper.Platform.PLATFORM_NAME_ANDROID) String platformName) {
        DesiredCapabilities caps = DriverHelper.getCaps(udid, deviceName, appPackage, appActivity, bundleId, platformName);
        caps.setCapability("noReset", false);
        driver = new DriverHelper().getNewAppDriver(caps);
        MasterAppPO masterMobilePO = new MasterAppPO(driver);
        wait = masterMobilePO.getWait();
    }

    /**
     * Realiza o processo de login no westwing club
     *
     * @param email
     * @param senha
     * @param incorretParams
     */
    @Parameters({"email", "senha", "incorretParams"})
    @Test
    public void realizarLogin(@Optional("kelvin.moreira@westwing.com.br") String email,
                              @Optional("123456") String senha,
                              @Optional("false") boolean incorretParams) {
        LoginPO loginPO = new LoginPO(driver);
        loginPO.realizarLogin(email, senha);
        if (incorretParams) {
            if (senha.length() < 6) {
                assertEquals(wait.until(ExpectedConditions.visibilityOf(loginPO.lblSenhaError)).isDisplayed(),
                        true, "Mensagem de erro, por dado invalido, apresentado como esperado");
            } else {
                assertEquals(wait.until(ExpectedConditions.visibilityOf(loginPO.lblErroInesperado)).isDisplayed(),
                        true, "Mensagem de erro, por dado invalido, apresentado como esperado");
            }
        } else {
            assertEquals(wait.until(ExpectedConditions.visibilityOf(loginPO.btnCarrinho)).isDisplayed(),
                    true, "Realizou login com sucesso");
        }
    }

    /**
     * Realiza o processo de teste no esqueci minha senha no westwing club
     *
     * @param email
     * @param finalizar
     */
    @Parameters({"email", "senha"})
    @Test
    public void acessarEsqueciMinhaSenha(@Optional("kelvin.moreira@westwing.com") String email,
                                         @Optional("true") boolean finalizar) {
        LoginPO loginPO = new LoginPO(driver);
        loginPO.acessarEsqueciMinhaSenha(email, finalizar);
        assertEquals(wait.until(ExpectedConditions.elementToBeClickable(loginPO.btnResetPassword)).isDisplayed(),
                true, "Acessou realizar a senha com sucesso");
    }


    /**
     * Realiza o processo de teste no registro de novos usuarios no westwing club
     *
     * @param email
     * @param senha
     * @param finalizar
     */
    @Parameters({"email", "senha", "finalizar"})
    @Test
    public void realizarCadastro(@Optional("genericUser@westwing.com") String email,
                                 @Optional("123456789") String senha,
                                 @Optional("true") boolean finalizar) {
        LoginPO loginPO = new LoginPO(driver);
        loginPO.realizarCadastro(email, senha, finalizar);
        if (finalizar) {
            assertEquals(wait.until(ExpectedConditions.visibilityOf(loginPO.toolbarHome)).isDisplayed(),
                    true, "Realizou a criação de um novo usuario com sucesso");
        } else {
            assertEquals(wait.until(ExpectedConditions.elementToBeClickable(loginPO.btnRestrarFinal)).isDisplayed(),
                    true, "Realizou o processo de acesso a página de registro e preencheu o dados com sucesso");
        }
    }


    /**
     * Finaliza a sessao do driver
     */
    @AfterMethod
    private void tearDown() {
        new ScreenshotManager().takeScreenShot(driver);
        driver.quit();
    }

}