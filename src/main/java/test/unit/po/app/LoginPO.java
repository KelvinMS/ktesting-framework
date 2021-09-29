package test.unit.po.app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static core.ExtentManager.info;

public class LoginPO extends MasterAppPO{
        public LoginPO(AppiumDriver<MobileElement> driverMobile) {
            super(driverMobile);
        }

        @AndroidFindBy(id = "br.westwing.android:id/loginButton")
        private List<MobileElement> btnLogin;

        @AndroidFindBy(id = "br.westwing.android:id/credential_edittext")
        private MobileElement txtEmail;

        @AndroidFindBy(id = "br.westwing.android:id/password_edittext")
        private MobileElement txtPassword;

        @AndroidFindBy(id = "br.westwing.android:id/login_button")
        private MobileElement btnMakeLogin;

        @AndroidFindBy(id = "br.westwing.android:id/cart_imageview")
        public MobileElement btnCarrinho;

        @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"br.westwing.android:id/snackbar_text\").textContains(\"Ops! Ocorreu um erro inesperado\")")
        public  MobileElement lblErroInesperado;

        @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"br.westwing.android:id/textinput_error\").textContains(\"A senha precisa ter no mínimo 6 caracteres\")")
        public  MobileElement lblSenhaError;


        @AndroidFindBy(id = "br.westwing.android:id/password_forgotten_text")
        private MobileElement btnForgotPassword;

        @AndroidFindBy(id = "br.westwing.android:id/email_forgottenPasword")
        private MobileElement txtEmailForgotPassword;

        @AndroidFindBy(id = "br.westwing.android:id/send_button")
        public MobileElement btnResetPassword;

        @AndroidFindBy(id = "br.westwing.android:id/toolbar")
        public MobileElement toolbarHome;


        @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ProgressBar\")")
        private MobileElement progressBar;

        @AndroidFindBy(id = "br.westwing.android:id/sign_up_button")
        private MobileElement btnRegister;

        @AndroidFindBy(id = "br.westwing.android:id/terms_conditions_switch")
        private MobileElement cbxTermsAndConditions;

        @AndroidFindBy(id = "br.westwing.android:id/register_button")
        public MobileElement btnRestrarFinal;



        /**
         * Realiza login no club
         * @param email
         * @param password
         * @return
         */
        public LoginPO realizarLogin(String email, String password){
            wait.until(ExpectedConditions.visibilityOf(btnLogin.get(0))).click();
            wait.until(ExpectedConditions.visibilityOf(txtEmail)).sendKeys(email);
            wait.until(ExpectedConditions.visibilityOf(txtPassword)).sendKeys(password);
            btnMakeLogin.click();
/*            if (btnLogin.size() > 0) {
                wait.until(ExpectedConditions.visibilityOf(btnLogin.get(0))).click();
                info("Clica no botão Login");
                wait.until(ExpectedConditions.visibilityOf(txtEmail)).sendKeys(email);
                info("Preenche o email");
                wait.until(ExpectedConditions.visibilityOf(txtPassword)).sendKeys(password);
                info("Preenche a senha");
                wait.until(ExpectedConditions.visibilityOf(btnMakeLogin)).click();
                info("Clica em realizar login");
            }*/
            return this;
        }

        public LoginPO acessarEsqueciMinhaSenha(String email, boolean finalizar){
            wait.until(ExpectedConditions.visibilityOf(btnLogin.get(0))).click();
            wait.until(ExpectedConditions.visibilityOf(btnForgotPassword)).click();
            wait.until(ExpectedConditions.visibilityOf(txtEmailForgotPassword)).sendKeys(email);
            if (finalizar){
                btnResetPassword.click();
                try {
                    wait.until(ExpectedConditions.invisibilityOf(progressBar));
                }
                catch (Exception e){
                    info("Loading infinito depois de clicar em resetar senha");
                }
            }
            return this;
        }

        /**
         *
         * @param email
         * @param password
         * @return
         */
        public LoginPO realizarCadastro(String email, String password, boolean finalizar){
            wait.until(ExpectedConditions.visibilityOf(btnRegister)).click();
            info("Clica no botao Cadastrar");
            String randomEmail = String.valueOf(Math.random()) +"@gmail.com";
            if (finalizar){
                wait.until(ExpectedConditions.visibilityOf(txtEmail)).sendKeys(randomEmail);
                info("Digitar email");
                wait.until(ExpectedConditions.visibilityOf(txtPassword)).sendKeys(password);
                info("Digitar senha");
                cbxTermsAndConditions.click();
                info("Aceitar os termos e condicoes");
                wait.until(ExpectedConditions.visibilityOf(btnRestrarFinal)).click();
                info("Clica em Cadastrar");
            }else {
                wait.until(ExpectedConditions.visibilityOf(txtEmail)).sendKeys(email);
                info("Digitar email");
                wait.until(ExpectedConditions.visibilityOf(txtPassword)).sendKeys(password);
                info("Digitar senha");
                cbxTermsAndConditions.click();
                info("Aceitar os termos e condicoes");
            }
            return this;
        }

    }

