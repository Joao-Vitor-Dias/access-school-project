package com.accesses.administrative_system.message_api;

import com.accesses.administrative_system.entity.Student;
import com.accesses.administrative_system.service.StudentService;
import com.accesses.administrative_system.util.TelephoneFormatter;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.NoSuchElementException;

@Component
@Getter
@Setter
public class Message {

    private static StudentService studentService;
    private static WebDriver driver;
    private static QrImageTreatment qrImageTreatment;

    public Message(StudentService studentServiceParm){
        studentService = studentServiceParm;
    }

    public static void startWhatsapp(){

        driver = new ChromeDriver();

        qrImageTreatment = new QrImageTreatment();

        driver.manage().window().maximize();

        driver.get("https://web.whatsapp.com");

    }

    public static byte[] getQrCode() throws IOException {

        String pathImgQr = "/home/dr-john/Developer/intellij-java/access_project/back-end/administrative-system/src/main/resources/images-qr/qr.jpg";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("canvas[aria-label='Scan this QR code to link a device!']")));

        File screenshot = ((TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);

        Path dest = Paths.get(pathImgQr);

        Files.copy(screenshot.toPath(),dest, StandardCopyOption.REPLACE_EXISTING);

        return qrImageTreatment.getImageAsBytes(pathImgQr);

    }

    public static byte[] getPrint() throws IOException {

        String pathImg = "/home/dr-john/Developer/intellij-java/access_project/back-end/administrative-system/src/main/resources/images-qr/print.jpg";

        File screenshot = ((TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);

        Path dest = Paths.get(pathImg);

        Files.copy(screenshot.toPath(),dest, StandardCopyOption.REPLACE_EXISTING);

        return qrImageTreatment.getImageAsBytes(pathImg);

    }

    public static void sendMessage(MessageRequest messageRequest) {

        String textInputPath = "/html/body/div[1]/div/div[1]/div[3]/div/div[4]/div/footer/div[1]/div/span/div/div[2]/div/div[3]/div[1]";

        Student student = studentService.findStudentById(messageRequest.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Estudante nao encontrado!! "));

        driver.get("https://web.whatsapp.com/send?phone=" + TelephoneFormatter.telephoneFormatter(student.getTelephone()));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(textInputPath)));

        WebElement textInput = driver.findElement(By.xpath(textInputPath));

        textInput.sendKeys(messageRequest.getMessage());

        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[3]/div/div[4]/div/footer/div[1]/div/span/div/div[2]/div/div[4]/div/span/div/div/div[1]/div[1]/span")).click();

    }

    public static void quitWhatsapp(){
        driver.quit();
    }

}
