package com.accesses.administrative_system.message_api;

import com.accesses.administrative_system.entity.Student;
import com.accesses.administrative_system.message_api.utils.QrImageTreatment;
import com.accesses.administrative_system.service.StudentService;
import com.accesses.administrative_system.util.TelephoneFormatter;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.NoSuchElementException;

@Component
@Getter
@Setter
public class Message {

    @Getter
    private static List<String> historyNumber = new ArrayList<>();
    private static StudentService studentService;
    private static WebDriver driver;
    private static QrImageTreatment qrImageTreatment;

    public Message(StudentService studentServiceParm){
        studentService = studentServiceParm;
    }

    public static void startWhatsapp(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=new",            // Headless moderno
                "--no-sandbox",              // Evita erro de sandbox do Linux
                "--disable-dev-shm-usage",   // Evita falta de memória compartilhada
                "--disable-gpu",             // Necessário para alguns containers
                "--remote-allow-origins=*"  // Evita erros de CORS com ChromeDriver 114+
        );

        driver = new ChromeDriver(options);

        qrImageTreatment = new QrImageTreatment();

        driver.manage().window().maximize();

        driver.get("https://web.whatsapp.com");

    }

    public static byte[] getQrCode() throws IOException {

        String pathImgQr = "/app/images/qr.jpg";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("canvas[aria-label='Scan this QR code to link a device!']")));

        File screenshot = ((TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);

        Path dest = Paths.get(pathImgQr);

        Files.copy(screenshot.toPath(),dest, StandardCopyOption.REPLACE_EXISTING);

        return qrImageTreatment.getImageAsBytes(pathImgQr);

    }

    public static byte[] getPrint() throws IOException {

        String pathImg = "/app/images/print.jpg";

        File screenshot = ((TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);

        Path dest = Paths.get(pathImg);

        Files.copy(screenshot.toPath(),dest, StandardCopyOption.REPLACE_EXISTING);

        return qrImageTreatment.getImageAsBytes(pathImg);

    }

    public static void sendMessage(MessageStudentRequest messageStudentRequest) throws InterruptedException {

        String textInputPath = "//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div/div[3]/div[1]/p";
        String sendButton = "//*[@id=\"main\"]/footer/div[1]/div/span/div/div[2]/div/div[4]/div/span/div/div/div[1]/div[1]/span";
        
        List<Long> studentsId = messageStudentRequest.getStudentsId();

        for (Long studentId : studentsId){

            Student student = getStudentFunction(studentId);

            if (messageStudentRequest.isDefaultMessage()){
                String message = getMessageFunction(student);
                messageStudentRequest.setMessage(message);
            }

            driver.get("https://web.whatsapp.com/send?phone=" + TelephoneFormatter.telephoneFormatter(student.getTelephone()));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(textInputPath)));

            WebElement textInput = driver.findElement(By.xpath(textInputPath));

            textInput.sendKeys(messageStudentRequest.getMessage());

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sendButton)));

            driver.findElement(By.xpath(sendButton)).click();

            Thread.sleep(100);

            historyNumber.add("Telefone: " + student.getTelephone());

        }

    }

    public static void sendGenericMessageWay(GenericMessageRequest messageRequest) throws InterruptedException {

        if (messageRequest.message() == null){
            throw new RuntimeException("Message can`t be null");
        }

        // //*[@id="main"]/footer/div[1]/div/span/div/div/div/div[3]/div[1]/p
        String textInputPath = "//footer//div[@role='textbox' and @contenteditable='true']";

        // //*[@id="main"]/footer/div[1]/div/span/div/div/div/div[4]/div/span/button
        String sendButton = "(//*[@id=\"main\"]/footer//button)[last()]";

        List<String> studentsNumbers = Arrays.stream(messageRequest.numbers().trim().split(",")).toList();

        for (String number : studentsNumbers){

            if(number.length() > 11){
                throw new RuntimeException("Numero invalido");
            }

            System.out.println("Url da service:  https://web.whatsapp.com/send?phone=" + TelephoneFormatter.telephoneFormatter(number));
            driver.get("https://web.whatsapp.com/send?phone=" + TelephoneFormatter.telephoneFormatter(number));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textInputPath)));

            WebElement textInput = driver.findElement(By.xpath(textInputPath));

            textInput.sendKeys(messageRequest.message());

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sendButton)));

            driver.findElement(By.xpath(sendButton)).click();

            Thread.sleep(2000);

            historyNumber.add("Telefone: " + number + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        }


    }



    public static void quitWhatsapp(){
        driver.quit();
    }

    private static Student getStudentFunction(Long studentId){


        return studentService.findStudentById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Estudante nao encontrado!! "));

    }

    private static String getMessageFunction(Student student){

        return "Bom dia !!! " + student.getFirstName() + " tudo bem? ";

    }

}
