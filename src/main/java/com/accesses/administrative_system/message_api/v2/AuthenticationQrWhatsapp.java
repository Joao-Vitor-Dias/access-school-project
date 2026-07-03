package com.accesses.administrative_system.message_api.v2;

import com.accesses.administrative_system.message_api.config.SeleniumManager;
import com.accesses.administrative_system.message_api.v2.constants.SeleniumConstants;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
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
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class AuthenticationQrWhatsapp implements GetAuthentication{

    private final SeleniumManager seleniumManager;


    public AuthenticationQrWhatsapp(SeleniumManager seleniumManager) {
        this.seleniumManager = seleniumManager;
    }

    @Override
    public void execute() throws IOException {

        String pathImgQr = SeleniumConstants.QR_CODE_PATH;

        WebDriver webDriver = seleniumManager.getWebDriver();

        webDriver.get("https://web.whatsapp.com");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        //"canvas[aria-label='Scan this QR code to link a device!']"
        wait.until(d ->
                !d.findElements(By.cssSelector("canvas")).isEmpty()
                ||
                !d.findElements(By.cssSelector("[data-testid='chat-list']")).isEmpty()
        );

        boolean hasQr = !webDriver.findElements(
                By.cssSelector("canvas[aria-label='Scan this QR code to link a device!']")
        ).isEmpty();

        if (hasQr) {
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

            Path dest = Paths.get(pathImgQr);

            Files.copy(screenshot.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        }else {
            throw new RuntimeException("Verify if you is already logged ... ");
        }

        //return qrImageTreatment.getImageAsBytes(pathImgQr);

    }
}
