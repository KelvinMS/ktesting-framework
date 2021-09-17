package core;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;

public class ScreenshotManager {

    //TODO IMPORTANTE
    // https://www.tutorialspoint.com/create-a-temporary-file-in-java#:~:text=A%20temporary%20file%20can%20be,createTempFile().
    private static final String FILEPATH = System.getProperty("user.dir") + "/target/ExtentReport";
    private static final String FILENAME = "screenshot.png";
    public static String imagemBASE64;

    public void takeScreenShot(WebDriver driver) {
        try {
            File imagem = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(imagem, new File(FILEPATH+"/" + FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error trying to take screenshot...");
        }
        String imagemBASE64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
