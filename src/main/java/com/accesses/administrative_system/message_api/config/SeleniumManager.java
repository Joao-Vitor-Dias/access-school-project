package com.accesses.administrative_system.message_api.config;


import com.accesses.administrative_system.exceptions.SeleniumNotInitializedException;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.net.URL;


@Getter
@Component
public class SeleniumManager {

    private WebDriver webDriver;

    public void connect() {

        if (webDriver != null){
            return;
        }

        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--user-data-dir=/home/seluser/chrome-profile",
                "--headless=new",            // Headless moderno
                "--no-sandbox",              // Evita erro de sandbox do Linux
                "--disable-dev-shm-usage",   // Evita falta de memória compartilhada
                "--disable-gpu",             // Necessário para alguns containers
                "--remote-allow-origins=*"  // Evita erros de CORS com ChromeDriver 114+
        );

        try {
            URL seleniumUrl = new URL("http://selenium:4444");

            webDriver = new RemoteWebDriver(seleniumUrl, options);
        }catch (Exception e){
            throw new SeleniumNotInitializedException("Erro em conseguir se comunicar com o container do selenium");
        }
    }

    public void disconnect() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }

}
