package core;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ScreenshotManager {



    //TODO IMPORTANTE
    // https://www.tutorialspoint.com/create-a-temporary-file-in-java#:~:text=A%20temporary%20file%20can%20be,createTempFile().
    private static final String SCREENSHOTPATH = Paths.get(System.getProperty("user.dir"),"target","ExtentReport").toString();
    private static final String FILENAME = "screenshot.png";
    public static String imagemBASE64;
    private static final ThreadLocal<File> compressedImageFile = new ThreadLocal();
    private static final String PREVIEW_ICON_RESOURCE_NAME = "screenshotPreviewIcon.png";
    private static final String TAG_HTML_BR = "<br />";
    private String screenshotPreviewIconBase64;
    private float compressionValue;

    public void takeScreenShot(WebDriver driver) {
        try {
            File imagem = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(imagem, new File(Paths.get(SCREENSHOTPATH,FILENAME).toString()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error trying to take screenshot...");
        }
        String imagemBASE64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        //System.out.println("\n\nBase64 o metodo antigo:"+imagemBASE64);
    }

    public void takeScreenshotToReport(ThreadLocal<WebDriver> driver,String message,ScreenshotManager.ScreenshotStatus stepStatus){
        String screenshot = this.getReportExtentScreenshot(driver);

        //System.out.println("\n*****  SET O STATUS DA SCREENSHOT E ADICIONA NO REPORT ******");
        switch(stepStatus) {
            case PASS:
                ExtentTestListeners.getInstance().pass(message + screenshot);
                break;
            case FAIL:
                ExtentTestListeners.getInstance().fail(message + screenshot);
                break;
            case INFO:
                ExtentTestListeners.getInstance().info(message + screenshot);
                break;
            case WARNING:
                ExtentTestListeners.getInstance().warning(message + screenshot);
                break;
            case DEBUG:
                ExtentTestListeners.getInstance().debug(message + screenshot);
        }
    }


    public String getReportExtentScreenshot(ThreadLocal<WebDriver> driver) {
        return this.getExtentReportScreenshot((WebDriver)driver.get());
    }

    public String getExtentReportScreenshot(WebDriver driver) {
        String base64Image = this.getImagemBASE64(driver);
        StringBuilder sb = new StringBuilder();
        sb.append("<div align=\"right\"><ul class='screenshots right'>");
        sb.append("<li><img data-featherlight=\"image\" href=\"data:image/jpeg;charset=utf-8;base64, ");
        sb.append(base64Image);// CRIAR
        sb.append("\"  src=\"data:image/png;base64, ");
        sb.append(base64Image);// CRIAR  // VAI TER PREVIEW
        sb.append("\" alt=\"T4GEX SCREENSHOT\" width=\"20%\" /></img></li>");
        sb.append("</ul></div>");
        return sb.toString();
    }

    public String getImagemBASE64(WebDriver driver) {
        try {
            File compressedFile = this.screenshotCompressor((File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
            byte[] compressedByteArray = Files.readAllBytes(compressedFile.toPath());
            this.cleanupFile();
            return Base64.getEncoder().encodeToString(compressedByteArray);
        }catch (IOException var4){
            System.out.println("Error taking screenshot as Base64");
            return null;
        }
    }



    private File screenshotCompressor(File pngFileToCompress) throws IOException {
        BufferedImage pngImage = ImageIO.read(pngFileToCompress);
        //LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        BufferedImage jpegImage = new BufferedImage(pngImage.getWidth(), pngImage.getHeight(), 1);
        jpegImage.createGraphics().drawImage(pngImage, 0, 0, Color.WHITE, (ImageObserver)null);
        compressedImageFile.set(File.createTempFile("compressed_image_" + getRandomPath(), ".jpg"));
        ((File)compressedImageFile.get()).deleteOnExit();
        OutputStream os = new FileOutputStream((File)compressedImageFile.get());
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = (ImageWriter)writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(2);
        param.setCompressionQuality((float) 0.3);
        writer.write((IIOMetadata)null, new IIOImage(jpegImage, (List)null, (IIOMetadata)null), param);
        os.close();
        ios.close();
        writer.dispose();
        this.cleanupFile(pngFileToCompress.toPath());
        return (File)compressedImageFile.get();
    }


    private void cleanupFile(Path pathToFile) {
        try {
            Files.delete(pathToFile);
        } catch (IOException var3) {
            System.out.println("Error trying to delete compressed image file\nERROR: "+var3+"");
        }
    }

    private void cleanupFile() {
        try {
            Files.delete(((File)compressedImageFile.get()).toPath());
            compressedImageFile.remove();
        } catch (IOException var2) {
            System.out.println("Error trying to delete compressed image file\nERROR: "+var2+"");
        }

    }

    private static String getRandomPath() {
        long start = 1L;
        long end = 99999L;
        return String.valueOf(ThreadLocalRandom.current().nextLong(start, end));
    }

    public static enum ScreenshotStatus {
        PASS,
        FAIL,
        INFO,
        WARNING,
        DEBUG;

        private ScreenshotStatus() {
        }
    }
}
