package com.accesses.administrative_system.message_api.config;


import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SeleniumManager {

    private WebDriver webDriver;

    public void connect(){

        if (webDriver != null){
            return;
        }

        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--user-data-dir=./chrome-profile",
                "--headless=new",            // Headless moderno
                "--no-sandbox",              // Evita erro de sandbox do Linux
                "--disable-dev-shm-usage",   // Evita falta de memória compartilhada
                "--disable-gpu",             // Necessário para alguns containers
                "--remote-allow-origins=*"  // Evita erros de CORS com ChromeDriver 114+
        );

        webDriver = new ChromeDriver(options);
    }

    public void disconnect() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

}
